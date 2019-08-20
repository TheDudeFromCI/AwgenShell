package net.whg.awgenshell.util.template;

import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.util.CommandResult;

/**
 * This interface is designed for lambda implementations for running a running a
 * specific subcommand with the given arguments.
 *
 * @author TheDudeFromCI
 */
public interface SubCommandExecutor
{
	/**
	 * Runs the given subcommand with the given inputs.
	 *
	 * @param env
	 *     - The shell environment to operate within.
	 * @param args
	 *     - The input arguments.
	 * @param flags
	 *     - All flags provided for this subcommand.
	 * @return The command result for this command execution.
	 */
	CommandResult run(ShellEnvironment env, InputArgument[] args, CommandFlag[] flags);
}
