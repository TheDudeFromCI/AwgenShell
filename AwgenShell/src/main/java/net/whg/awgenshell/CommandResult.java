package net.whg.awgenshell;

/**
 * Represents the immutable output of command execution.
 *
 * @author TheDudeFromCI
 */
public class CommandResult
{
	/**
	 * A shorthand for <code>new CommandResult("", false, true)</code>, assuming the
	 * command sends the error message to the user directly.
	 */
	public static final CommandResult ERROR = new CommandResult("", false, true);

	/**
	 * A shorthand for <code>new CommandResult("", true, false)</code>, assuming the
	 * command has no outputs.
	 */
	public static final CommandResult SUCCESS = new CommandResult("", true, false);

	private final String value;
	private final boolean normalExit;
	private final boolean capturesConsole;

	/**
	 * Creates a new command result instance.
	 *
	 * @param value
	 *     - The returned value from the command execution.
	 * @param normalExit
	 *     - True if the command exited normally, false if an error occured or
	 *     faulty input was provided.
	 * @param capturesConsole
	 *     - If true, this function will not print the output the the sender, if
	 *     this command is not piped into another command.
	 */
	public CommandResult(String value, boolean normalExit, boolean capturesConsole)
	{
		this.value = value;
		this.normalExit = normalExit;
		this.capturesConsole = capturesConsole;
	}

	/**
	 * Gets the output value of this command execution.
	 *
	 * @return The output value.
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Checks if the commend has exited normally.
	 *
	 * @return True if the command exited normally, false if an error occured.
	 */
	public boolean isNormalExit()
	{
		return normalExit;
	}

	/**
	 * Checks if this command handled its own console output. If true, this command
	 * prevents the shell from printing to the console automatically.
	 *
	 * @return Whether or not this command had captured the console.
	 */
	public boolean capturesConsole()
	{
		return capturesConsole;
	}
}
