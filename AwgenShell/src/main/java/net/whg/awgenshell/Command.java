package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is used to represent a command that can be excuted.
 *
 * @author TheDudeFromCI
 */
class Command
{
	private static Logger logger = LoggerFactory.getLogger(Command.class);

	/**
	 * Attempts to create an argument value using the next few tokens if possible.
	 *
	 * @param env
	 *     - The environment to compile the arguments in.
	 * @param tokenizer
	 *     - The tokenizer to supply the tokens.
	 * @return An argument value if one could be made. Null otherwise.
	 */
	private static ArgumentValue consumeTokensArgument(ShellEnvironment env, Tokenizer tokenizer)
	{
		Token next = tokenizer.peekNextToken();

		switch (next.getType())
		{
			case TokenTemplate.VARIABLE:
				tokenizer.consumeToken();
				return new VariableArgument(env.getVariable(next.getFormattedValue()));

			case TokenTemplate.SOFT_STRING:
			case TokenTemplate.HARD_STRING:
			case TokenTemplate.QUOTED_STRING:
				tokenizer.consumeToken();
				return new StringArgument(next.getFormattedValue());

			case TokenTemplate.FORMAT_STRING:
				tokenizer.consumeToken();
				return new FormattedStringArgument(next.getFormattedValue(), env);

			case TokenTemplate.OPEN_PARENTHESIS_SYMBOL:
			{
				tokenizer.consumeToken();

				Input input = Input.consumeTokens(env, tokenizer);

				Token closer = tokenizer.nextToken();
				if (closer.getType() != TokenTemplate.CLOSE_PARENTHESIS_SYMBOL)
					throw new CommandParseException("Unexpected token: " + closer.getValue() + "!");

				return new CommandArgument(input, true);
			}

			case TokenTemplate.OPEN_CURLY_BRACKET_SYMBOL:
			{
				tokenizer.consumeToken();

				Input input = Input.consumeTokens(env, tokenizer);

				Token closer = tokenizer.nextToken();
				if (closer.getType() != TokenTemplate.CLOSE_CURLY_BRACKET_SYMBOL)
					throw new CommandParseException("Unexpected token: " + closer.getValue() + "!");

				return new CommandArgument(input, false);
			}
		}

		return null;
	}

	/**
	 * Attempts to create a command by consuming as many tokens as possible.
	 *
	 * @param env
	 *     - The environment to compile this command in.
	 * @param tokenizer
	 *     - The tokenizer to supply the tokens.
	 * @return A command if one could be made, null otherwise.
	 */
	static Command consumeTokens(ShellEnvironment env, Tokenizer tokenizer)
	{
		Token cmdName = tokenizer.peekNextToken();

		if (cmdName.getType() != TokenTemplate.SOFT_STRING)
			return null;
		tokenizer.consumeToken();

		String commandName = cmdName.getFormattedValue();
		List<ArgumentValue> arguments = new ArrayList<>();

		if (tokenizer.hasNextToken())
		{
			ArgumentValue argument = consumeTokensArgument(env, tokenizer);
			if (argument != null)
			{
				arguments.add(argument);

				while (tokenizer.hasNextToken())
				{
					Token comma = tokenizer.peekNextToken();
					if (comma.getType() == TokenTemplate.COMMA_SYMBOL)
					{
						tokenizer.consumeToken();

						argument = consumeTokensArgument(env, tokenizer);
						if (argument != null)
							arguments.add(argument);
						else
							throw new CommandParseException(
									"Unexpected token: " + tokenizer.nextToken().getValue() + "!");
					}
					else
					{
						argument = consumeTokensArgument(env, tokenizer);
						if (argument != null)
							arguments.add(argument);
						else
							break;
					}
				}
			}
		}

		ArgumentValue[] args = arguments.toArray(new ArgumentValue[arguments.size()]);
		return new Command(commandName, env.getCommand(commandName), args);
	}

	private final String commandName;
	private final ArgumentValue[] arguments;
	private final CommandHandler command;

	/**
	 * Creates a new command grammer instance.
	 *
	 * @param command
	 *     - The command handler for this command.
	 * @param arguments
	 *     - The arguments for the command.
	 */
	private Command(String commandName, CommandHandler command, ArgumentValue[] arguments)
	{
		this.commandName = commandName;
		this.command = command;
		this.arguments = arguments;
	}

	/**
	 * Executes this command within the given environment.
	 *
	 * @param environment
	 *     - The environment to execute this command in.
	 * @return The outcome of the command execution.
	 */
	CommandResult execute(ShellEnvironment environment)
	{
		for (ArgumentValue arg : arguments)
			if (arg instanceof CommandArgument)
				((CommandArgument) arg).evaluate();

		if (command == null)
		{
			environment.getCommandSender().println("Unknown command: '" + commandName + "'!");
			return new CommandResult("", false, true);
		}

		try
		{
			return command.execute(environment, arguments);
		}
		catch (Exception e)
		{
			environment.getCommandSender()
					.println("There was an internal error while running the command '" + commandName + "'.");
			logger.error("Error thrown while running the command: '" + commandName + "'.", e);
			return CommandResult.ERROR;
		}
	}
}
