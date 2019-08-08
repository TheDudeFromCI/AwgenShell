package net.whg.awgenshell.util.template;

import net.whg.awgenshell.arg.ArgumentValue;

/**
 * A command template is a format for how a command should be laid out including
 * common patterns like named subcommands, wildcard arguments number arguments,
 * etc.
 *
 * @author TheDudeFromCI
 */
public class CommandTemplate
{
	private final SubCommand[] subcommands;

	/**
	 * Creates a new command template based on the given subcommands.
	 *
	 * @param subcommands
	 *     - The list of subcommands which make up this template.
	 */
	public CommandTemplate(SubCommand[] subcommands)
	{
		this.subcommands = subcommands;
	}

	/**
	 * Retrieves the first matching subcommand for the given input arguments.
	 *
	 * @param args
	 *     - The raw input arguments.
	 * @param values
	 *     - The solved values for the given arguments to prevent repeated solving
	 *     of indirect commands.
	 * @return The first matching subcommand within this command template, or null
	 *     if there is no matching subcommand.
	 */
	public SubCommand getSubcommand(ArgumentValue[] args, String[] values)
	{
		for (SubCommand sub : subcommands)
			if (sub.matches(args, values))
				return sub;

		return null;
	}
}
