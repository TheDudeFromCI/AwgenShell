package net.whg.awgenshell;

class Token
{
	private int type;
	private String value;
	private String formattedValue;
	private int pos;

	public Token(int type, String value, int pos)
	{
		this.type = type;
		this.value = value;
		this.pos = pos;
		formattedValue = value;
	}

	public int getType()
	{
		return type;
	}

	public String getValue()
	{
		return value;
	}

	public String getFormattedValue()
	{
		return formattedValue;
	}

	public void setFormattedValue(String value)
	{
		formattedValue = value;
	}

	public int getPos()
	{
		return pos;
	}
}
