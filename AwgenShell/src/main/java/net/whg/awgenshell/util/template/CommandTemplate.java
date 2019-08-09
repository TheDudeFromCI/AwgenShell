package net.whg.awgenshell.util.template;

import java.util.List;
import net.whg.awgenshell.perms.PermissionNode;

/**
 * A command template is a format for how a command should be laid out including
 * common patterns like named subcommands, wildcard arguments number arguments,
 * etc.
 *
 * @author TheDudeFromCI
 */
public class CommandTemplate
{
	private final String name;
	private final String[] aliases;
	private final PermissionNode permissions;
	private final SubCommand[] subcommands;

	/**
	 * Creates a new command template based on the given subcommands.
	 *
	 * @param name
	 *     - The name of this command.
	 * @param aliases
	 *     - An array of aliases for this command.
	 * @param subcommands
	 *     - The list of subcommands which make up this template.
	 */
	public CommandTemplate(String name, String[] aliases, PermissionNode permissions, SubCommand[] subcommands)
	{
		this.name = name;
		this.aliases = aliases;
		this.permissions = permissions;
		this.subcommands = subcommands;
	}

	/**
	 * Retrieves the first matching subcommand for the given input arguments.
	 *
	 * @param args
	 *     - The input arguments.
	 * @return The first matching subcommand within this command template, or null
	 *     if there is no matching subcommand.
	 */
	public SubCommand getSubcommand(List<InputArgument> args)
	{
		for (SubCommand sub : subcommands)
			if (sub.matches(args))
				return sub;

		return null;
	}

	/**
	 * Gets the name of this command.
	 *
	 * @return The name.
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Gets an array of all aliases for this command.
	 *
	 * @return All aliases for this command.
	 */
	public String[] getAliases()
	{
		return aliases;
	}

	/**
	 * Gets the permission node for this subcommand.
	 *
	 * @return The permission node.
	 */
	public PermissionNode getPermissions()
	{
		return permissions;
	}
}
