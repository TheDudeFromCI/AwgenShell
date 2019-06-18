package net.whg.awgenshell;

public class CommandParser
{
	public static void parse(String input)
	{
		Tokenizer tokens = new Tokenizer(input);

		System.out.println("Starting tokenization:");
		while (tokens.hasNextToken())
			System.out.println("  " + tokens.nextToken().getValue());
	}
}
