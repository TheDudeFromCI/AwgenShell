package net.whg.awgenshell.util.template;

import java.util.List;

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
	public int matchArguments(List<InputArgument> args, int offset)
	{
		if (offset >= args.size())
			return -1;

		InputArgument a = args.get(offset);

		if (a.isIndirectCommand())
			return -1;

		if (!a.getLast().equals(word))
			return -1;

		return 1;
	}
}
