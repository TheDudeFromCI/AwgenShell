package net.whg.awgenshell;

public class CommandVariable
{
    private String _name;
    private String _value = "";

    public CommandVariable(String name)
    {
        _name = name;
    }

    public String getValue()
    {
        return _value;
    }

    public void setValue(String value)
    {
        _value = value;
    }

    public String getName()
    {
        return _name;
    }
}
