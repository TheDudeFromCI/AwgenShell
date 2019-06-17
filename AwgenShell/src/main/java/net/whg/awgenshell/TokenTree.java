package net.whg.awgenshell;

import java.util.LinkedList;
import java.util.List;

public class TokenTree
{
	private TokenTreePattern[] patterns =
	{
	/* @formatter:off */

		new TokenTreePattern("ap*;?"),
		new TokenTreePattern("v=ap*;?"),
		new TokenTreePattern("v=s;?"),
		new TokenTreePattern("v=n;?"),
		new TokenTreePattern("v=v;?"),
		new TokenTreePattern(";"),

		/* @formatter:on */
	};
	private List<Token> _tokens = new LinkedList<>();

	public void addTokens(Tokenizer tokenizer)
	{
		while (tokenizer.hasNextToken())
			_tokens.add(tokenizer.nextToken());
	}

	public boolean hasNextPath()
	{
		return _tokens.size() > 0;
	}

	public TokenPath nextPath()
	{
		for (TokenTreePattern pattern : patterns)
		{
			TokenPath path = pattern.build(_tokens);
			if (path != null)
				return path;
		}

		throw new CommandParseException("Incorrectly formatted command!");
	}
}
