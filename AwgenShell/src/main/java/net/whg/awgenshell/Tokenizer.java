package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;
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
		new TokenTemplate(TokenTemplate.OPEN_PARENTHESIS_SYMBOL, "^(\\()"),
		new TokenTemplate(TokenTemplate.CLOSE_PARENTHESIS_SYMBOL, "^(\\))"),
		new TokenTemplate(TokenTemplate.SEMICOLON_SYMBOL, "^(\\;)"),
		new TokenTemplate(TokenTemplate.OPEN_CURLY_BRACKET_SYMBOL, "^(\\{)"),
		new TokenTemplate(TokenTemplate.CLOSE_CURLY_BRACKET_SYMBOL, "^(\\})"),
		new TokenTemplate(TokenTemplate.PIPE_SYMBOL, "^(\\|)"),
		// @formatter:on
	};

	private List<Token> tokens = new ArrayList<>();
	private int index;

	public Tokenizer(String code)
	{
		code = code.replace('\n', ' ');

		while (!code.isEmpty())
		{
			code = code.trim();

			boolean parsedNext = false;
			for (TokenTemplate tem : tokenTemplates)
			{
				Matcher matcher = tem.getPattern().matcher(code);
				if (matcher.find())
				{
					String token = matcher.group().trim();
					code = matcher.replaceAll("");

					Token t = new Token(tem.getType(), token);
					formatToken(t);

					tokens.add(t);

					parsedNext = true;
					break;
				}
			}

			if (!parsedNext)
				throw new CommandParseException("Failed to parse " + code + "!");
		}
	}

	public Token nextToken()
	{
		if (index >= tokens.size())
			throw new CommandParseException("Unexpected end of line!");

		return tokens.get(index++);
	}

	public Token peekNextToken()
	{
		if (index >= tokens.size())
			throw new CommandParseException("Unexpected end of line!");

		return tokens.get(index);
	}

	public void consumeToken()
	{
		index++;
	}

	public int getPosition()
	{
		return index;
	}

	public Token getToken(int index)
	{
		if (index >= tokens.size())
			throw new CommandParseException("Unexpected end of line!");

		return tokens.get(index);
	}

	private void formatToken(Token token)
	{
		switch (token.getType()) {
			case TokenTemplate.QUOTED_STRING:
			case TokenTemplate.FORMAT_STRING:
				token.setFormattedValue(token.getValue().substring(1, token.getValue().length() - 1));
				break;
			case TokenTemplate.VARIABLE:
				token.setFormattedValue(token.getValue().substring(1));
				break;
		}
	}

	public boolean hasNextToken()
	{
		return index < tokens.size();
	}

	public void setPosition(int pos)
	{
		index = pos;
	}
}
