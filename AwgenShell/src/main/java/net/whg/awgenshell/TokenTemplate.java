package net.whg.awgenshell;

import java.util.regex.Pattern;

public class TokenTemplate
{
	public static final int ARGUMENT_STRING = 0;
	public static final int QUOTED_STRING = 1;
	public static final int FORMAT_STRING = 2;
	public static final int VARIABLE = 3;
	public static final int SOFT_STRING = 4;
	public static final int HARD_STRING = 5;
	public static final int EQUALS_SYMBOL = 6;
	public static final int COMMA_SYMBOL = 7;
	public static final int AND_SYMBOL = 8;
	public static final int OPEN_PARENTHeSIS_SYMBOL = 9;
	public static final int CLOSE_PARENTHeSIS_SYMBOL = 10;
	public static final int SEMICOLON_SYMBOL = 11;
	public static final int OPEN_CURLY_BRACKET_SYMBOL = 12;
	public static final int CLOSE_CURLY_BRACKET_SYMBOL = 13;
	public static final int PIPE_SYMBOL = 14;

	private int type;
	private Pattern pattern;

	public TokenTemplate(int type, String regex)
	{
		this.type = type;
		pattern = Pattern.compile(regex);
	}

	public Pattern getPattern()
	{
		return pattern;
	}

	public int getType()
	{
		return type;
	}
}
