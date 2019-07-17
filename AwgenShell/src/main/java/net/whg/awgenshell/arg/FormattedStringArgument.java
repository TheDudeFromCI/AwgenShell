package net.whg.awgenshell;

/**
 * A class representing a formatting string argument, which acts as a string,
 * that replaces variable names within the string to the current variables
 * value.
 *
 * @author TheDudeFromCI
 */
public class FormattedStringArgument implements ArgumentValue
{
	private String string;
	private ShellEnvironment env;

	/**
	 * Creates a new formatted string argument instance.
	 *
	 * @param string
	 *     - The string value.
	 * @param env
	 *     - The environment to pull variables from.
	 */
	public FormattedStringArgument(String string, ShellEnvironment env)
	{
		this.string = string;
		this.env = env;
	}

	@Override
	public String getValue()
	{
		String val = string;

		for (Variable v : env.getVariables())
			if (val.contains("$" + v.getName()))
				val = val.replace("$" + v.getName(), v.getValue());

		val = val.replaceAll("%n", "\n");

		return val;
	}
}
