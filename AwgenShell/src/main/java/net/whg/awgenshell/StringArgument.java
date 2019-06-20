package net.whg.awgenshell;

/**
 * A command argument that represents a simple string input.
 *
 * @author TheDudeFromCI
 */
public class StringArgument implements ArgumentValue
{
	private String value;

	/**
	 * Creates a new string argument instance.
	 * 
	 * @param value
	 *     - The string.
	 */
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
