package net.whg.awgenshell;

public class VariableArgument implements CommandArgument
{
	private CommandVariable var;

	public VariableArgument(CommandVariable var)
	{
		this.var = var;
	}

	@Override
	public String getValue()
	{
		return var.getValue();
	}

	@Override
	public String toString()
	{
		return "$" + var.getName();
	}

	public void setValue(String value)
	{
		var.setValue(value);
	}
}
