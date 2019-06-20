package net.whg.awgenshell;

/**
 * A collection of common utility functions for handling events within
 * AwgenShell.
 * 
 * @author TheDudeFromCI
 */
public class ShellUtils
{
	/**
	 * Converts the input string into a boolean output safely. The value is
	 * considered true if the value is not empty, whitespace, "0", or "false" (not
	 * case sensitive).
	 *
	 * @param val
	 *     - The value to try to parse.
	 * @return True if the value can be considered true, false otherwise.
	 */
	public static boolean stringToBoolean(String val)
	{
		if (val.isBlank())
			return false;
		if (val.equals("0"))
			return false;
		if (val.equalsIgnoreCase("false"))
			return false;

		return true;
	}
}
