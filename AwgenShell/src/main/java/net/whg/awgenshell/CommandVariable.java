package net.whg.awgenshell;

public class CommandVariable
{
	private String name;
	private String value = "";

	public CommandVariable(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getName()
	{
		return name;
	}
}
