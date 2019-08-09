package net.whg.awgenshell.util.template;

import java.util.List;

/**
 * A command template argument is a compiled pattern which is used to match with
 * input arguments.
 *
 * @author TheDudeFromCI
 */
public interface CommandTemplateArg
{
	/**
	 * This function tries to match as many elements from a given set of input
	 * arguments as this pattern allows.
	 *
	 * @param args
	 *     - The input arguments to check against.
	 * @param offset
	 *     - The position within the argument list to start checking at.
	 * @return The number of elements matched, or negative -1 if the arguments do
	 *     are not compatible with this template.
	 */
	int matchArguments(List<InputArgument> args, int offset);

	/**
	 * This function checks if this results of the pattern should remove the input
	 * arguments from being checked at the end. If true, after the subcommand is
	 * successfully matched, the arguments checked by matchArguments are removed
	 * from the list. Defaults to false.
	 *
	 * @return True if this pattern should remove arguments, false otherwise.
	 */
	default boolean pruneArgs()
	{
		return false;
	}
}
