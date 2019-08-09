package net.whg.awgenshell.util.template;

import java.util.List;

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
	public int matchArguments(List<InputArgument> args, int offset)
	{
		if (args.size() <= offset)
			return -1;

		InputArgument a = args.get(offset);
		if (a.isIndirectCommand())
			return -1;

		if (floating)
		{
			try
			{
				Float.valueOf(a.getLast());
				return 1;
			}
			catch (NumberFormatException e)
			{
				return -1;
			}
		}

		try
		{
			Integer.valueOf(a.getLast());
			return 1;
		}
		catch (NumberFormatException e)
		{
			return -1;
		}
	}
}
