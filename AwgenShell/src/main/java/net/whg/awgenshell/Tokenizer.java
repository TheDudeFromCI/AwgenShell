package net.whg.awgenshell;

import java.util.regex.Matcher;

public class Tokenizer
{
	private TokenTemplate[] tokenTemplates =
	{
		// @formatter:off
		new TokenTemplate(TokenTemplate.SOFT_STRING, "^([a-zA-Z][a-zA-Z0-9\\\\-_]*)"),
		new TokenTemplate(TokenTemplate.HARD_STRING, "^([a-zA-Z0-9\\-_\\/\\\\\\?\\!\\:\\.]+)"),
		new TokenTemplate(TokenTemplate.QUOTED_STRING, "^(\\\"(?:[^\"\\\\])*\\\"|\\'(?:[^'\\\\])*\\')"),
		new TokenTemplate(TokenTemplate.FORMAT_STRING, "^(\\`(?:[^`\\\\])*\\`)"),
		new TokenTemplate(TokenTemplate.VARIABLE, "^(\\$[a-zA-Z][a-zA-Z0-9\\\\-_]*)"),
		new TokenTemplate(TokenTemplate.EQUALS_SYMBOL, "^(\\=)"),
		new TokenTemplate(TokenTemplate.COMMA_SYMBOL, "^(\\,)"),
		new TokenTemplate(TokenTemplate.AND_SYMBOL, "^(\\&)"),
		new TokenTemplate(TokenTemplate.OPEN_PARENTHeSIS_SYMBOL, "^(\\()"),
		new TokenTemplate(TokenTemplate.CLOSE_PARENTHeSIS_SYMBOL, "^(\\))"),
		new TokenTemplate(TokenTemplate.SEMICOLON_SYMBOL, "^(\\;)"),
		new TokenTemplate(TokenTemplate.OPEN_CURLY_BRACKET_SYMBOL, "^(\\{)"),
		new TokenTemplate(TokenTemplate.CLOSE_CURLY_BRACKET_SYMBOL, "^(\\})"),
		new TokenTemplate(TokenTemplate.PIPE_SYMBOL, "^(\\|)"),
		// @formatter:on
	};

	private String code;

	public Tokenizer(String code)
	{
		this.code = code.replace('\n', ' ');
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
