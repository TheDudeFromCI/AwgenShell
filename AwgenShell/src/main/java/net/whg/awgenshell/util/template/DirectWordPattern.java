package net.whg.awgenshell.util.template;

/**
 * Matches true when the input argument matches the given word.
 *
 * @author TheDudeFromCI
 */
public class DirectWordPattern implements CommandTemplateArg
{
	private final String word;

	public DirectWordPattern(String word)
	{
		this.word = word;
	}

	@Override
	public int matchArguments(InputArgument[] args, int offset)
	{
		if (offset >= args.length)
			return -1;

		if (args[offset].isIndirectCommand())
			return -1;

		if (!args[offset].getLast().equals(word))
			return -1;

		return 1;
	}
}
