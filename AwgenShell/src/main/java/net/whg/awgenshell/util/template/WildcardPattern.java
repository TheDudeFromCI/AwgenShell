package net.whg.awgenshell.util.template;

import java.util.List;

/**
 * Allows for a single wildcard argument, or an infinite number of them. Will
 * always return true for any match. May be marked as optional or required.
 * Infinite wildcards and optional wildcards will always take as many as
 * possible unless there are no more options to take.
 *
 * @author TheDudeFromCI
 */
public class WildcardPattern implements CommandTemplateArg
{
	private final boolean infinite;
	private final boolean optional;

	public WildcardPattern(boolean infinite, boolean optional)
	{
		this.infinite = infinite;
		this.optional = optional;
	}

	@Override
	public int matchArguments(List<InputArgument> args, int offset, SubCommand sub)
	{
		if (args.size() <= offset)
			return optional ? 0 : -1;

		if (infinite)
			return args.size() - offset;

		return 1;
	}

	@Override
	public int giveBack(List<InputArgument> args, SubCommand sub, int offset, int length)
	{
		if (length > 1)
			return 1;

		return optional ? 1 : 0;
	}
}
