package net.whg.awgenshell;

public class Token
{
    private int _type;
    private String _value;

    public Token(int type, String value)
    {
        _type = type;
        _value = value;
    }

    public int getType()
    {
        return _type;
    }

    public String getValue()
    {
        return _value;
    }
}
