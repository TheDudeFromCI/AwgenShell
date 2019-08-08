package net.whg.awgenshell.util.template;

/**
 * Matches true if there are not more arguments after this pattern. False
 * otherwise.
 *
 * @author TheDudeFromCI
 */
public class EndOfLinePattern implements CommandTemplateArg
{
	@Override
	public int matchArguments(InputArgument[] args, int offset)
	{
		if (offset == args.length)
			return 0;

		return -1;
	}
}
