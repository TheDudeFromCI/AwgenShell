package net.whg.awgenshell.util.template;

/**
 * This shows the template for how a command flag should be setup. This tells
 * the compiler how a command flag should be interpreted.
 *
 * @author TheDudeFromCI
 */
public class CommandFlagTemplate
{
	private final String name;
	private final int numberValues;

	/**
	 * Creates a new command template with the flag name and the number of input
	 * arguments it expects.
	 *
	 * @param name
	 *     - The name of the command flag, including the dash.
	 * @param numberValues
	 *     - The number of values this flag expects.
	 */
	public CommandFlagTemplate(String name, int numberValues)
	{
		if (!name.startsWith("-"))
			throw new IllegalArgumentException("Flag name must start with a dash!");

		if (numberValues < 0)
			throw new IllegalArgumentException("Flag cannot require a negative number of values!");

		this.name = name;
		this.numberValues = numberValues;
	}

	/**
	 * Gets the name of this flag.
	 * 
	 * @return The name of this flag.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets the number of inputs for this flag.
	 * 
	 * @return
	 */
	public int getNumberOfValues()
	{
		return numberValues;
	}
}
