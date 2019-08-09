package net.whg.awgenshell.util.template;

import java.util.List;

/**
 * This pattern checks for an arbitrary number of flag arguments and returns the
 * number of flags found.
 *
 * @author TheDudeFromCI
 */
public class FlagsPattern implements CommandTemplateArg
{
	@Override
	public int matchArguments(List<InputArgument> args, int offset)
	{
		int count = 0;
		for (int i = offset; i < args.size(); i++)
		{
			InputArgument a = args.get(i);

			if (a.isIndirectCommand())
				break;

			if (!a.getLast().startsWith("-"))
				break;

			count++;
		}

		return count;
	}

	@Override
	public boolean pruneArgs()
	{
		return true;
	}
}
