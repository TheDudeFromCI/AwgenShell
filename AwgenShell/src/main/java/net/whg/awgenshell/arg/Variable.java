package net.whg.awgenshell.arg;

/**
 * A name variable that is stored within a shell environment. A variable
 * contains a single string value.
 *
 * @author TheDudeFromCI
 */
public class Variable
{
	private String name;
	private String value;

	/**
	 * Creates a new variable instance, with the current value as an empty string.
	 *
	 * @param name
	 *     - The name of this variable.
	 */
	public Variable(String name)
	{
		this.name = name;
		value = "";
	}

	/**
	 * Gets the name of this variable.
	 *
	 * @return The name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the current value of this variable.
	 *
	 * @return The value.
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Assigns a new value to this variable.
	 *
	 * @param value
	 *     - The value to assign.
	 */
	public void setValue(String value)
	{
		this.value = value;
	}
}
