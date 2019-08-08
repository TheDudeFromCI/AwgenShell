package net.whg.awgenshell.util.template;

import net.whg.awgenshell.arg.ArgumentValue;

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
	public int matchArguments(ArgumentValue[] args, String[] values, int offset)
	{
		if (offset >= values.length)
			return -1;

		if (!values[offset].equals(word))
			return -1;

		return 1;
	}
}
