package net.whg.awgenshell;

import java.util.regex.Pattern;

public class TokenTemplate
{
    public static final int STANDARD = 0;
    public static final int VARIABLE = 1;
    public static final int SYMBOL = 2;
    public static final int STRING = 3;
    public static final int EMPTY = 4;
    public static final int NESTED_COMMAND = 5;
    public static final int DYNAMIC_VARIABLE = 6;

    private int _type;
    private Pattern _pattern;

    public TokenTemplate(int type, String regex)
    {
        _type = type;
        _pattern = Pattern.compile(regex);
    }

    public Pattern getPattern()
    {
        return _pattern;
    }

    public int getType()
    {
        return _type;
    }
}
