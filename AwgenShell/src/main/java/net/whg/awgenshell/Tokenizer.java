package net.whg.awgenshell;

import java.util.regex.Matcher;

public class Tokenizer
{
	private TokenTemplate[] _tokenTemplates =
	{
		new TokenTemplate(TokenTemplate.STRING, "^(\".*\"(?=$|\\s+|;|=|,))"),
		new TokenTemplate(TokenTemplate.NESTED_COMMAND, "^(\\(.*\\)(?=$|\\s+|;|=|,))"),
		new TokenTemplate(TokenTemplate.STANDARD, "^([a-zA-Z0-9_/.-]+(?=$|\\s+|;|=|,))"),
		new TokenTemplate(TokenTemplate.VARIABLE, "^(\\$[a-zA-Z][a-zA-Z0-9_-]*(?=$|\\s+|;|=|,))"),
		new TokenTemplate(TokenTemplate.DYNAMIC_VARIABLE, "^(\\$\\[.*\\](?=\\s+|$|;|=|,))"),
		new TokenTemplate(TokenTemplate.SYMBOL, "^(\\=)"), new TokenTemplate(TokenTemplate.SYMBOL, "^(\\;)"),
		new TokenTemplate(TokenTemplate.SYMBOL, "^(\\,)"),
	};

	private String _code;

	public Tokenizer(String code)
	{
		_code = code.replace('\n', ' ');
	}

	public Token nextToken()
	{
		_code = _code.trim();

		if (_code.isEmpty())
			return new Token(TokenTemplate.EMPTY, "");

		for (TokenTemplate tem : _tokenTemplates)
		{
			Matcher matcher = tem.getPattern().matcher(_code);
			if (matcher.find())
			{
				String token = matcher.group().trim();
				_code = matcher.replaceAll("");

				if (tem.getType() == TokenTemplate.STRING || tem.getType() == TokenTemplate.NESTED_COMMAND)
					return new Token(tem.getType(), token.substring(1, token.length() - 1));

				if (tem.getType() == TokenTemplate.DYNAMIC_VARIABLE)
					return new Token(tem.getType(), token.substring(2, token.length() - 1));

				return new Token(tem.getType(), token);
			}
		}

		throw new CommandParseException("Failed to parse " + _code + "!");
	}

	public boolean hasNextToken()
	{
		return !_code.isEmpty();
	}
}
