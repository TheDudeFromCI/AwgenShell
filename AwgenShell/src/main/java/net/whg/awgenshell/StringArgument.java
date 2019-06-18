package net.whg.awgenshell;

public class StringArgument implements ArgumentValue
{
	private String value;

	public StringArgument(String value)
	{
		this.value = value;
	}

	@Override
	public String getValue()
	{
		return value;
	}
}
