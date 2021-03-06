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
	 * @param sub
	 *     - The subcommand which this pattern belongs to.
	 * @return The number of elements matched, or negative -1 if the arguments do
	 *     are not compatible with this template.
	 */
	int matchArguments(List<InputArgument> args, int offset, SubCommand sub);

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

	/**
	 * If the next argument cannot return a match, this function is called to give
	 * back arguments in an attempt to get the next argument to find a match. This
	 * method is recalled repeatedly until the next argument pattern forms a match,
	 * or this argument pattern is unable to give back any more arguments.
	 *
	 * @param args
	 *     - The input arguments to check against.
	 * @param sub
	 *     - The subcommand which this pattern belongs to.
	 * @param offset
	 *     - The position within the argument list to start checking at.
	 * @param length
	 *     - The number of arguments that are currently attached to to this pattern.
	 * @return The smallest number of arguments this command argument is able to
	 *     give back, or 0 if this pattern cannot give back any arguments and still
	 *     allow a match to occur.
	 */
	default int giveBack(List<InputArgument> args, SubCommand sub, int offset, int length)
	{
		return 0;
	}
}
