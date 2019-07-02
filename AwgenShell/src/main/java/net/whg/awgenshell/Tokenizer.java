package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

class Tokenizer
{
	private String tokenTerminator = "(?=$|\\s|\\=|\\,|\\&|\\(|\\)|\\{|\\}|\\;|\\|))";
	private String symbolTerminator = ")";

	private TokenTemplate[] tokenTemplates =
	{
		// @formatter:off
		new TokenTemplate(TokenTemplate.SOFT_STRING, "^([a-zA-Z][a-zA-Z0-9\\\\-_]*", tokenTerminator),
		new TokenTemplate(TokenTemplate.HARD_STRING, "^([a-zA-Z0-9\\-_\\/\\\\\\?\\!\\:\\.]+", tokenTerminator),
		new TokenTemplate(TokenTemplate.QUOTED_STRING, "^(\"(?:[^\"\\\\]|\\\\.)*\"|'(?:[^'\\\\]|\\\\.)*", tokenTerminator),
		new TokenTemplate(TokenTemplate.FORMAT_STRING, "^(`(?:[^`\\\\]|\\\\.)*`", tokenTerminator),
		new TokenTemplate(TokenTemplate.VARIABLE, "^(\\$[a-zA-Z][a-zA-Z0-9\\\\-_]*", tokenTerminator),
		new TokenTemplate(TokenTemplate.EQUALS_SYMBOL, "^(\\=", symbolTerminator),
		new TokenTemplate(TokenTemplate.COMMA_SYMBOL, "^(\\,", symbolTerminator),
		new TokenTemplate(TokenTemplate.AND_SYMBOL, "^(\\&", symbolTerminator),
		new TokenTemplate(TokenTemplate.OPEN_PARENTHESIS_SYMBOL, "^(\\(", symbolTerminator),
		new TokenTemplate(TokenTemplate.CLOSE_PARENTHESIS_SYMBOL, "^(\\)", symbolTerminator),
		new TokenTemplate(TokenTemplate.SEMICOLON_SYMBOL, "^(\\;", symbolTerminator),
		new TokenTemplate(TokenTemplate.OPEN_CURLY_BRACKET_SYMBOL, "^(\\{", symbolTerminator),
		new TokenTemplate(TokenTemplate.CLOSE_CURLY_BRACKET_SYMBOL, "^(\\}", symbolTerminator),
		new TokenTemplate(TokenTemplate.PIPE_SYMBOL, "^(\\|", symbolTerminator),
		// @formatter:on
	};

	private List<Token> tokens = new ArrayList<>();
	private int index;

	public Tokenizer(String code)
	{
		code = safeRemoveNewlines(code);

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

	private String safeRemoveNewlines(String code)
	{
		if (code.indexOf('\n') == -1)
			return code;

		char[] c = code.toCharArray();

		boolean inQuotes = false;
		char quoteType = ' ';
		for (int i = 0; i < c.length; i++)
		{
			if (c[i] == '\n')
			{
				if (!inQuotes)
					c[i] = ' ';
				continue;
			}

			if (c[i] != '"' && c[i] != '\'' && c[i] != '`')
				continue;

			if (inQuotes)
			{
				if (quoteType == c[i] && (i == 0 || i > 0 && c[i - 1] != '\\'))
					inQuotes = false;
			}
			else
			{
				inQuotes = true;
				quoteType = c[i];
			}
		}

		return new String(c);
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
		switch (token.getType())
		{
			case TokenTemplate.QUOTED_STRING:
			case TokenTemplate.FORMAT_STRING:
				token.setFormattedValue(token.getValue().substring(1, token.getValue().length() - 1)
						.replace("\\\"", "\"").replace("\\'", "'").replace("\\`", "`"));
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
