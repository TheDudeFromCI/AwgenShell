package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

public class Command implements GrammerStack
{
	private String commandName;
	private ArgumentValue[] arguments;
	private CommandHandler command;

	public String execute(ShellEnvironment environment)
	{
		for (ArgumentValue arg : arguments)
			if (arg instanceof CommandArgument)
				((CommandArgument) arg).evaluate();

		if (command == null)
			command = environment.getCommand(commandName);

		if (command == null)
		{
			environment.getCommandSender().println("Unknown command: '" + commandName + "'!");
			return "false";
		}

		return command.execute(environment.getCommandSender(), arguments);
	}

	@Override
	public boolean consumeTokens(ShellEnvironment env, Tokenizer tokenizer)
	{
		Token commandName = tokenizer.nextToken();

		if (commandName.getType() != TokenTemplate.SOFT_STRING)
			return false;

		this.commandName = commandName.getFormattedValue();

		List<Argument> arguments = new ArrayList<>();

		if (tokenizer.hasNextToken())
		{
			Argument argument = new Argument();
			if (argument.consumeTokens(env, tokenizer))
			{
				arguments.add(argument);

				while (tokenizer.hasNextToken())
				{
					Token comma = tokenizer.peekNextToken();

					if (comma.getType() == TokenTemplate.COMMA_SYMBOL)
					{
						argument = new Argument();
						if (argument.consumeTokens(env, tokenizer))
							arguments.add(argument);
						else
							throw new CommandParseException(
									"Unexpected token: " + tokenizer.nextToken().getValue() + "!");
					}
					else
					{
						argument = new Argument();
						if (argument.consumeTokens(env, tokenizer))
							arguments.add(argument);
						else
							break;
					}
				}
			}
		}

		this.arguments = new ArgumentValue[arguments.size()];
		for (int i = 0; i < arguments.size(); i++)
			this.arguments[i] = arguments.get(i).getValue();

		return true;
	}
}
