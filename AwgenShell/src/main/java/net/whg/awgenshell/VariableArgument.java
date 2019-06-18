package net.whg.awgenshell;

public class VariableArgument implements ArgumentValue
{
	private Variable variable;

	public VariableArgument(Variable variable)
	{
		this.variable = variable;
	}

	@Override
	public String getValue()
	{
		return variable.getValue();
	}
}
