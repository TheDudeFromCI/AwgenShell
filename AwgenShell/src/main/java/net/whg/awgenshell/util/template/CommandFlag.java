package net.whg.awgenshell.util.template;

/**
 * A command flag is a property which can be assigned to a command to configure
 * how it should process the given information. A flag may contain an optional
 * setting, and all commands have a default value.
 *
 * @author TheDudeFromCI
 */
public class CommandFlag
{
	private final String name;
	private final String value;

	/**
	 * Creates a new command flag obejct with the given name and value.
	 *
	 * @param name
	 *     - The name of the flag. All flags should start with a dash and include no
	 *     spaces.
	 * @param value
	 *     - The value for the flag.
	 */
	public CommandFlag(String name, String value)
	{
		this.name = name;
		this.value = value;
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
	 * Gets the value of this flag.
	 * 
	 * @return The value.
	 */
	public String getValue()
	{
		return value;
	}
}
