package net.whg.awgenshell;

public class StringArgument implements CommandArgument
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

	@Override
	public String toString()
	{
		return value;
	}
}
