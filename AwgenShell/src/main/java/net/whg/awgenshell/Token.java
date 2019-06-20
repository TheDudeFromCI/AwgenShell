package net.whg.awgenshell;

class Token
{
	private int type;
	private String value;
	private String formattedValue;

	public Token(int type, String value)
	{
		this.type = type;
		this.value = value;
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
}
