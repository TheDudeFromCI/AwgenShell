package net.whg.awgenshell.util.template;

import net.whg.awgenshell.arg.ArgumentValue;

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
	 * @param arg
	 *     - The raw arguments to check against.
	 * @param value
	 *     - The solved values for the arguments, to prevent repeated solves for
	 *     indirect commands.
	 * @param offset
	 *     - The position within the argument list to start checking at.
	 * @return The number of elements matched, or negative -1 if the arguments do
	 *     are not compatible with this template.
	 */
	int matchArguments(ArgumentValue[] args, String[] values, int offset);
}
