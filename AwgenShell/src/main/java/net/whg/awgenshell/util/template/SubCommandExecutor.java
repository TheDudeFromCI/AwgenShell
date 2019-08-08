package net.whg.awgenshell.util.template;

import net.whg.awgenshell.arg.ArgumentValue;
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
	 * @param arg
	 *     - The raw arguments to check against.
	 * @param value
	 *     - The solved values for the arguments, to prevent repeated solves for
	 *     indirect commands.
	 * @return The command result for this command execution.
	 */
	CommandResult run(ShellEnvironment env, ArgumentValue[] args, String[] values);
}
