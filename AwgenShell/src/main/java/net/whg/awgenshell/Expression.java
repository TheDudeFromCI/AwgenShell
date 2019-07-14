package net.whg.awgenshell;

/**
 * A class which represents a command and optional variable assignment.
 *
 * @author TheDudeFromCI
 */
class Expression
{
	/**
	 * Attempts to create a new expression grammer by consuming as many tokens as
	 * possible.
	 *
	 * @param env
	 *     - The environment to compile this grammer in.
	 * @param tokenizer
	 *     - The tokenizer to supply the code tokens.
	 * @return An expression is one could be made, null otherwise.
	 */
	static Expression consumeTokens(ShellEnvironment env, Tokenizer tokenizer)
	{
		if (!tokenizer.hasNextToken())
			return null;

		int pos = tokenizer.getPosition();
		Token token = tokenizer.peekNextToken();

		Command command = null;
		Variable output = null;

		if (token.getType() == TokenTemplate.VARIABLE)
		{
			tokenizer.consumeToken();

			output = env.getVariable(token.getFormattedValue());

			Token equalsSign = tokenizer.nextToken();
			if (equalsSign.getType() != TokenTemplate.EQUALS_SYMBOL)
				throw new CommandParseException("Unexpected token!", equalsSign);
		}

		command = Command.consumeTokens(env, tokenizer);
		if (command == null)
		{
			if (output != null)
				throw new CommandParseException("Unexpected token!", tokenizer.nextToken());

			tokenizer.setPosition(pos);
			return null;
		}

		return new Expression(command, output);
	}

	private final Command command;
	private final Variable output;
	private final boolean isTempVar;

	private Expression(Command command, Variable output)
	{
		this.command = command;
		this.output = output == null ? new Variable("0") : output;
		isTempVar = output == null;
	}

	/**
	 * Executes this expression.
	 *
	 * @param environment
	 *     - The environment to execute this expression in.
	 * @param isDirectCommand
	 *     - True is this expression is being run within a direct command, false
	 *     otherwise.
	 * @return
	 */
	CommandResult execute(ShellEnvironment environment, boolean isDirectCommand)
	{
		CommandResult out = command.execute(environment);

		output.setValue(out.getValue());

		if (isTempVar && !out.capturesConsole() && !isDirectCommand)
			environment.getCommandSender().println(out.getValue());

		return out;
	}
}
