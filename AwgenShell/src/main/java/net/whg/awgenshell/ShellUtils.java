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
		if (val.matches("\\s+"))
			return false;
		if (val.equals("0"))
			return false;
		if (val.equalsIgnoreCase("false"))
			return false;

		return true;
	}

	/**
	 * Checks if the given input is an integer value.
	 *
	 * @param val
	 *     - The input.
	 * @return True if the value can be represented as an integer, false otherwise.
	 */
	public static boolean isInteger(String val)
	{
		return val.matches("[\\-]?[0-9]+");
	}

	/**
	 * Converts the given input in a string to an integer. If the value cannot be
	 * represented as a integer, 0 is returned.
	 *
	 * @param val
	 *     - The value to convert.
	 * @return A integer representation of the input.
	 */
	public static int asInt(String val)
	{
		try
		{
			return Integer.valueOf(val);
		}
		catch (Exception e)
		{
			return 0;
		}
	}

	/**
	 * Checks if the given input is a float value.
	 *
	 * @param val
	 *     - The input.
	 * @return True if the value can be represented as an float, false otherwise.
	 */
	public static boolean isFloat(String val)
	{
		return val.matches("[\\-]?([0-9]+\\\\.[0-9]*|[0-9]*\\\\.[0-9]+|[0-9]+)");
	}

	/**
	 * Converts the given input in a string to a float. If the value cannot be
	 * represented as a float, 0 is returned.
	 *
	 * @param val
	 *     - The value to convert.
	 * @return A integer representation of the input.
	 */
	public static float asFloat(String val)
	{
		try
		{
			return Float.valueOf(val);
		}
		catch (Exception e)
		{
			return 0;
		}
	}
}
