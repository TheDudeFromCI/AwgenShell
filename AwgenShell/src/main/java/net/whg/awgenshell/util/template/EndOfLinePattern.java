package net.whg.awgenshell.util.template;

import java.util.List;

/**
 * Matches true if there are not more arguments after this pattern. False
 * otherwise.
 *
 * @author TheDudeFromCI
 */
public class EndOfLinePattern implements CommandTemplateArg
{
	@Override
	public int matchArguments(List<InputArgument> args, int offset)
	{
		if (offset == args.size())
			return 0;

		return -1;
	}
}
