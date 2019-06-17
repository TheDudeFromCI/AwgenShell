package net.whg.awgenshell;

import java.util.regex.Matcher;

public class Tokenizer
{
	private TokenTemplate[] tokenTemplates =
	{
		new TokenTemplate(TokenTemplate.STRING, "^(\".*\"(?=$|\\s+|;|=|,))"),
		new TokenTemplate(TokenTemplate.NESTED_COMMAND, "^(\\(.*\\)(?=$|\\s+|;|=|,))"),
		new TokenTemplate(TokenTemplate.STANDARD, "^([a-zA-Z0-9_/.-]+(?=$|\\s+|;|=|,))"),
		new TokenTemplate(TokenTemplate.VARIABLE, "^(\\$[a-zA-Z][a-zA-Z0-9_-]*(?=$|\\s+|;|=|,))"),
		new TokenTemplate(TokenTemplate.DYNAMIC_VARIABLE, "^(\\$\\[.*\\](?=\\s+|$|;|=|,))"),
		new TokenTemplate(TokenTemplate.SYMBOL, "^(\\=)"), new TokenTemplate(TokenTemplate.SYMBOL, "^(\\;)"),
		new TokenTemplate(TokenTemplate.SYMBOL, "^(\\,)"),
	};

	private String code;

	public Tokenizer(String code)
	{
		code = code.replace('\n', ' ');
	}

	public Token nextToken()
	{
		code = code.trim();

		if (code.isEmpty())
			return new Token(TokenTemplate.EMPTY, "");

		for (TokenTemplate tem : tokenTemplates)
		{
			Matcher matcher = tem.getPattern().matcher(code);
			if (matcher.find())
			{
				String token = matcher.group().trim();
				code = matcher.replaceAll("");

				if (tem.getType() == TokenTemplate.STRING || tem.getType() == TokenTemplate.NESTED_COMMAND)
					return new Token(tem.getType(), token.substring(1, token.length() - 1));

				if (tem.getType() == TokenTemplate.DYNAMIC_VARIABLE)
					return new Token(tem.getType(), token.substring(2, token.length() - 1));

				return new Token(tem.getType(), token);
			}
		}

		throw new CommandParseException("Failed to parse " + code + "!");
	}

	public boolean hasNextToken()
	{
		return !code.isEmpty();
	}
}
