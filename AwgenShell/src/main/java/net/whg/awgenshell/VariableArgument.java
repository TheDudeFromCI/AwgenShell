package net.whg.awgenshell;

/**
 * A variable argumemt. This argument simple returns the current value of the
 * variable when is was built with.
 *
 * @author TheDudeFromCI
 */
public class VariableArgument implements ArgumentValue
{
	private Variable variable;

	/**
	 * Creates a new variable argument instance.
	 *
	 * @param variable
	 *     - The variable this argument represents.
	 */
	public VariableArgument(Variable variable)
	{
		this.variable = variable;
	}

	@Override
	public String getValue()
	{
		return variable.getValue();
	}

	/**
	 * Gets the variable this argument is attached to.
	 * 
	 * @return The variable;
	 */
	public Variable getVariable()
	{
		return variable;
	}
}
