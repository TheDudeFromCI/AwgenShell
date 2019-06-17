package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

public class TokenPatternSolver
{
	public static TokenPatternSolver[] compile(String pattern)
	{
		List<TokenPatternSolver> solvers = new ArrayList<>();

		for (int i = 0; i < pattern.length(); i++)
		{
			char type = pattern.charAt(i);
			char quan = '1';

			if (i < pattern.length() - 1)
			{
				char after = pattern.charAt(i + 1);
				if (after == '*' || after == '+' || after == '?')
				{
					quan = after;
					i++;
				}
			}

			solvers.add(new TokenPatternSolver(type, quan));
		}

		return solvers.toArray(new TokenPatternSolver[solvers.size()]);
	}

	private char type;
	private char quantifier;

	public TokenPatternSolver(char type, char quantifier)
	{
		this.type = type;
		this.quantifier = quantifier;
	}

	public char getType()
	{
		return type;
	}

	public char getQuantifier()
	{
		return quantifier;
	}

	public boolean matches(Token token)
	{
		switch (type) {
			case 'a':
				return token.getType() == TokenTemplate.STANDARD;

			case 'v':
				return token.getType() == TokenTemplate.VARIABLE;

			case 'n':
				return token.getType() == TokenTemplate.NESTED_COMMAND;

			case 's':
				return token.getType() == TokenTemplate.STRING;

			case 'd':
				return token.getType() == TokenTemplate.DYNAMIC_VARIABLE;

			case 'p':
				return token.getType() == TokenTemplate.STANDARD || token.getType() == TokenTemplate.STRING
						|| token.getType() == TokenTemplate.VARIABLE || token.getType() == TokenTemplate.NESTED_COMMAND
						|| token.getType() == TokenTemplate.DYNAMIC_VARIABLE
						|| token.getType() == TokenTemplate.SYMBOL && token.getValue().equals(",");

			case '=':
				return token.getType() == TokenTemplate.SYMBOL && token.getValue().equals("=");

			case ';':
				return token.getType() == TokenTemplate.SYMBOL && token.getValue().equals(";");

			default:
				throw new IllegalStateException("Unknown pattern type!");
		}
	}

	public boolean isGreedy()
	{
		return quantifier == '*' || quantifier == '+';
	}

	public boolean isOptional()
	{
		return quantifier == '?' || quantifier == '*';
	}

	public int count(List<Token> tokens, int index)
	{
		int count = 0;

		for (int i = index; i < tokens.size(); i++)
		{
			if (matches(tokens.get(i)))
			{
				count++;

				if (!isGreedy())
					break;
			}
			else
				break;
		}

		return count;
	}
}
