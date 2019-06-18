package net.whg.awgenshell;

public class Expression implements GrammerStack
{
	private Command command;
	private Variable output;
	private Variable tempVar;

	public boolean execute(ShellEnvironment environment)
	{
		String out = command.execute(environment);

		if (output != null)
			output.setValue(out);
		else
		{
			tempVar = environment.getVariable("0");
			tempVar.setValue(out);
		}

		return ShellUtils.stringToBoolean(out);
	}

	@Override
	public boolean consumeTokens(ShellEnvironment env, Tokenizer tokenizer)
	{
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

	public Variable getOutput()
	{
		if (output == null)
			return tempVar;
		return output;
	}
}
