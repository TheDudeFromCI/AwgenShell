package net.whg.awgenshell.util.template;

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
	public int matchArguments(InputArgument[] args, int offset)
	{
		if (args.length <= offset)
			return -1;

		if (args[offset].isIndirectCommand())
			return -1;

		if (floating)
		{
			try
			{
				Float.valueOf(args[offset].getLast());
				return 1;
			}
			catch (NumberFormatException e)
			{
				return -1;
			}
		}

		try
		{
			Integer.valueOf(args[offset].getLast());
			return 1;
		}
		catch (NumberFormatException e)
		{
			return -1;
		}
	}
}
