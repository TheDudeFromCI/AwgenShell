package net.whg.awgenshell;

/**
 * This is the base interface for all command implementations used within
 * AwgenShell. An instance of this class is added to a module and is used for
 * all executions of a command execution.
 *
 * @author TheDudeFromCI
 */
public interface CommandHandler
{
	/**
	 * The name of this command.
	 *
	 * @return The name.
	 */
	String getName();

	/**
	 * Executes this command.
	 *
	 * @param sender
	 *     - The user who issued this command.
	 * @param args
	 *     - The input arguments for this command.
	 * @return The output of this command. An output should always be provided,
	 *     regardless of whether the command was successfully executed or not. The
	 *     output should reflect the execution state of this command.
	 */
	CommandResult execute(CommandSender sender, ArgumentValue[] args);

	/**
	 * Gets an array of all aliases for this command, or an empty array if this
	 * command has no aliases.
	 *
	 * @return An array of all aliases for this command.
	 */
	String[] getAliases();
}
