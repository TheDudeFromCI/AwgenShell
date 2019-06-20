package net.whg.awgenshell;

/**
 * A class which represents a command and optional variable assignment.
 *
 * @author TheDudeFromCI
 */
class Expression implements GrammerStack
{
	private Command command;
	private Variable output;
	private boolean isTempVar;

	public CommandResult execute(ShellEnvironment environment, boolean isDirectCommand)
	{
		CommandResult out = command.execute(environment);

		if (output == null)
		{
			output = new Variable("0");
			isTempVar = true;
		}

		output.setValue(out.getValue());

		if (isTempVar && !out.capturesConsole() && !isDirectCommand)
			environment.getCommandSender().println(out.getValue());

		return out;
	}

	@Override
	public boolean consumeTokens(ShellEnvironment env, Tokenizer tokenizer)
	{
		if (!tokenizer.hasNextToken())
			return false;

		int pos = tokenizer.getPosition();

		Token token = tokenizer.peekNextToken();

		output = null;
		if (token.getType() == TokenTemplate.VARIABLE)
		{
			tokenizer.consumeToken();

			output = env.getVariable(token.getFormattedValue());

			Token equalsSign = tokenizer.nextToken();
			if (equalsSign.getType() != TokenTemplate.EQUALS_SYMBOL)
				throw new CommandParseException("Unexpected token: " + equalsSign.getValue() + "!");
		}

		command = new Command();
		if (!command.consumeTokens(env, tokenizer))
		{
			if (output != null)
				throw new CommandParseException("Unexpected token: " + tokenizer.nextToken().getValue() + "!");

			tokenizer.setPosition(pos);
			return false;
		}

		return true;
	}

	/**
	 * Gets the output variable of this expression.
	 *
	 * @return The output variable of this expression.
	 */
	public Variable getOutput()
	{
		return output;
	}
}
