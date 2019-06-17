package net.whg.awgenshell;

public class Token
{
	private int type;
	private String value;

	public Token(int type, String value)
	{
		this.type = type;
		this.value = value;
	}

	public int getType()
	{
		return type;
	}

	public String getValue()
	{
		return value;
	}
}
