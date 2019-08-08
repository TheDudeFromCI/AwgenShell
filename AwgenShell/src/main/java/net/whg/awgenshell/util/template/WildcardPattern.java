package net.whg.awgenshell.util.template;

/**
 * Allows for a single wildcard argument, or an infinite number of them. Will
 * always return true for any match. May be marked as optional or required.
 * Infinite wildcards and optional wildcards will always take unless there are
 * no more options to take.
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
	public int matchArguments(InputArgument[] args, int offset)
	{
		if (args.length <= offset)
			return optional ? 0 : -1;

		if (infinite)
			return args.length - offset;

		return 1;
	}
}
