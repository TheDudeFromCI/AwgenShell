package net.whg.awgenshell.util.template;

/**
 * A command flag is a property which can be assigned to a command to configure
 * how it should process the given information. A flag contains a set of
 * assigned values, if any are expected.
 *
 * @author TheDudeFromCI
 */
public class CommandFlag
{
	private final String name;
	private final String[] values;

	/**
	 * Creates a new command flag obejct with the given name and values.
	 *
	 * @param name
	 *     - The name of the flag. All flags should start with a dash and include no
	 *     spaces.
	 * @param value
	 *     - An array of all values, in order, for the flag.
	 */
	public CommandFlag(String name, String[] values)
	{
		this.name = name;
		this.values = values;
	}

	/**
	 * Gets the name of this flag.
	 *
	 * @return The name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the array of values of this flag.
	 *
	 * @return The values for this flag.
	 */
	public String[] getValues()
	{
		return values;
	}
}
