package net.whg.awgenshell.util.template;

import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.arg.CommandArgument;

/**
 * An input argument is a wrapper for ArgumentValues to allow for more control
 * of reading their state without executing them multiple times.
 *
 * @author TheDudeFromCI
 */
public class InputArgument
{
	private final ArgumentValue arg;
	private final boolean indirectCommand;
	private String lastValue;

	/**
	 * Creates a new input argument value.
	 *
	 * @param arg
	 *     - The argument to wrap around.
	 */
	public InputArgument(ArgumentValue arg)
	{
		this.arg = arg;

		if (arg instanceof CommandArgument)
			indirectCommand = !((CommandArgument) arg).isDirect();
		else
			indirectCommand = false;
	}

	/**
	 * Gets the current value of this input argument. If this input argument is an
	 * indirect command, this method returns null. For arguments which contain
	 * dynamic variables, these will automatically be updated with this method call.
	 *
	 * @return The value of the given argument, or null if this argument is an
	 *     indirect command.
	 */
	public String getValue()
	{
		if (indirectCommand)
			return null;

		return lastValue = arg.getValue();
	}

	/**
	 * Gets the last known value of this argument without updating it again. If this
	 * argument has not yet been solved for, it will be automatically solved.
	 * 
	 * @return The last known value of this input argument.
	 */
	public String getLast()
	{
		if (lastValue == null)
			lastValue = run();

		return lastValue;
	}

	/**
	 * This method acts exactly as <code>arg.getValue()</code> and is intended for
	 * operation with indirect commands.
	 *
	 * @return Updates and returns the valuie of this input argument, regardless of
	 *     its type.
	 */
	public String run()
	{
		return lastValue = arg.getValue();
	}

	/**
	 * Check if this variable represents an indirect command or not.
	 *
	 * @return True if this is an indirect command, false otherwise.
	 */
	public boolean isIndirectCommand()
	{
		return indirectCommand;
	}

	/**
	 * Gets the raw argument value this object is wrapping around.
	 *
	 * @return The argument value.
	 */
	public ArgumentValue getArgument()
	{
		return arg;
	}
}
