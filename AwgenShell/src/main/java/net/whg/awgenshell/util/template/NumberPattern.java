package net.whg.awgenshell.util.template;

import net.whg.awgenshell.arg.ArgumentValue;

/**
 * Returns true if the given input argument is a number.
 *
 * @author TheDudeFromCI
 */
public class NumberPattern implements CommandTemplateArg
{
	private final boolean floating;

	public NumberPattern(boolean floating)
	{
		this.floating = floating;
	}

	@Override
	public int matchArguments(ArgumentValue[] args, String[] values, int offset)
	{
		if (values.length <= offset)
			return -1;

		if (floating)
		{
			try
			{
				Float.valueOf(values[offset]);
				return 1;
			}
			catch (NumberFormatException e)
			{
				return -1;
			}
		}

		try
		{
			Integer.valueOf(values[offset]);
			return 1;
		}
		catch (NumberFormatException e)
		{
			return -1;
		}
	}
}
