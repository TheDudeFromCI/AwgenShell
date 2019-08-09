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
	public int matchArguments(List<InputArgument> args, int offset, SubCommand sub)
	{
		int count = 0;
		for (int i = offset; i < args.size(); i++)
		{
			InputArgument a = args.get(i);

			if (a.isIndirectCommand())
				break;

			if (!a.getLast().startsWith("-"))
				break;

			CommandFlagTemplate flag = getFlag(a.getLast(), sub);
			if (flag == null)
				throw new InvalidFlagsException("Unknown flag: " + a.getLast());

			boolean error = false;
			int j;
			for (j = 0; j < flag.getNumberOfValues(); j++)
			{
				if (i + j + 1 >= args.size())
				{
					error = true;
					break;
				}

				InputArgument flagArg = args.get(i + j + 1);
				if (flagArg.isIndirectCommand())
				{
					error = true;
					break;
				}

				String val = flagArg.getLast();
				if (val.startsWith("-"))
				{
					error = true;
					break;
				}

				count++;
				i++;
			}

			if (error)
				throw new InvalidFlagsException("Not enough flag arguments for flag " + flag.getName() + "! Needed: "
						+ flag.getNumberOfValues() + ",  Provided: " + j);

			count++;
		}

		return count;
	}

	private CommandFlagTemplate getFlag(String name, SubCommand sub)
	{
		for (CommandFlagTemplate flag : sub.getFlags())
			if (flag.getName().equals(name))
				return flag;
		return null;
	}

	@Override
	public boolean pruneArgs()
	{
		return true;
	}
}
