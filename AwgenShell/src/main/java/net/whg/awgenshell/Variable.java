package net.whg.awgenshell;

public class Variable
{
	private String name;
	private String value;

	public Variable(String name)
	{
		this.name = name;
		value = "";
	}

	public String getName()
	{
		return name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
}
