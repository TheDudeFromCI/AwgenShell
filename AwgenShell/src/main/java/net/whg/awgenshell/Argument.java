package net.whg.awgenshell;

/**
 * This class is used within the tokenizer to represent an abstract argument
 * being parsed.
 *
 * @author TheDudeFromCI
 */
class Argument implements GrammerStack
{
	private ArgumentValue value;

	@Override
	public boolean consumeTokens(ShellEnvironment env, Tokenizer tokenizer)
	{
		Token next = tokenizer.peekNextToken();

		switch (next.getType()) {
			case TokenTemplate.VARIABLE:
				tokenizer.consumeToken();
				value = new VariableArgument(env.getVariable(next.getFormattedValue()));
				return true;

			case TokenTemplate.SOFT_STRING:
			case TokenTemplate.HARD_STRING:
			case TokenTemplate.QUOTED_STRING:
				tokenizer.consumeToken();
				value = new StringArgument(next.getFormattedValue());
				return true;

			case TokenTemplate.FORMAT_STRING:
				tokenizer.consumeToken();
				value = new FormattedStringArgument(next.getFormattedValue(), env);
				return true;

			case TokenTemplate.OPEN_PARENTHESIS_SYMBOL:
			{
				tokenizer.consumeToken();

				Input input = new Input();
				input.consumeTokens(env, tokenizer);

				Token closer = tokenizer.nextToken();
				if (closer.getType() != TokenTemplate.CLOSE_PARENTHESIS_SYMBOL)
					throw new CommandParseException("Unexpected token: " + closer.getValue() + "!");

				value = new CommandArgument(input, env, true);

				return true;
			}

			case TokenTemplate.OPEN_CURLY_BRACKET_SYMBOL:
			{
				tokenizer.consumeToken();

				Input input = new Input();
				input.consumeTokens(env, tokenizer);

				Token closer = tokenizer.nextToken();
				if (closer.getType() != TokenTemplate.CLOSE_CURLY_BRACKET_SYMBOL)
					throw new CommandParseException("Unexpected token: " + closer.getValue() + "!");

				value = new CommandArgument(input, env, false);

				return true;
			}
		}

		return false;
	}

	/**
	 * Gets the current value and type of this argument. Will be null if the next
	 * token was unable to be parsed as an argument.
	 *
	 * @return The value and type of this argument.
	 */
	public ArgumentValue getValue()
	{
		return value;
	}
}
