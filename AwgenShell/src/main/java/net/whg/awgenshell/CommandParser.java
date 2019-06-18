package net.whg.awgenshell;

public class CommandParser
{
	public static Input parse(ShellEnvironment environment, String line)
	{
		Tokenizer tokenizer = new Tokenizer(line);

		Input input = new Input();
		input.consumeTokens(environment, tokenizer);

		if (tokenizer.hasNextToken())
			throw new CommandParseException("Unexpected token: " + tokenizer.nextToken().getValue() + "!");

		return input;
	}
}
