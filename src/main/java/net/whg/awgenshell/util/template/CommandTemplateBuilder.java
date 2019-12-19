package net.whg.awgenshell.util.template;

import java.util.LinkedList;
import java.util.List;
import net.whg.awgenshell.perms.PermissionNode;

/**
 * This class acts as a builder for creating new command templates.
 *
 * @author TheDudeFromCI
 */
public class CommandTemplateBuilder
{
	private String name;
	private List<String> aliases = new LinkedList<>();
	private List<SubCommand> subcommands = new LinkedList<>();
	private PermissionNode permission;

	/**
	 * Returns a builder for a new subcommand to add to this template.
	 *
	 * @param pattern
	 *     - The pattern for the subcommand.
	 * @param exector
	 *     - The executor for the subcommand.
	 * @return A subcommand builder.
	 */
	public SubCommandBuilder subcommand(String pattern, SubCommandExecutor executor)
	{
		return new SubCommandBuilder(this, pattern, executor);
	}

	/**
	 * Sets the name of this template.
	 *
	 * @param name
	 *     - The name.
	 * @return This object.
	 */
	public CommandTemplateBuilder name(String name)
	{
		this.name = name;
		return this;
	}

	/**
	 * Adds a new alias to this template.
	 *
	 * @param alias
	 *     - The alias to add.
	 * @return This object.
	 */
	public CommandTemplateBuilder alias(String alias)
	{
		aliases.add(alias);
		return this;
	}

	/**
	 * Sets the permission node which is required to use this subcommand.
	 *
	 * @param permission
	 *     - The permission node.
	 * @return This object.
	 */
	public CommandTemplateBuilder perm(String permission)
	{
		this.permission = new PermissionNode(permission);
		return this;
	}

	/**
	 * Compiles this template.
	 *
	 * @return The newly built template object.
	 */
	public CommandTemplate build()
	{
		String[] aliasList = aliases.toArray(new String[aliases.size()]);
		SubCommand[] subcommandList = subcommands.toArray(new SubCommand[subcommands.size()]);

		return new CommandTemplate(name, aliasList, permission, subcommandList);
	}

	/**
	 * An internal method which adds a subcommand to this command template.
	 *
	 * @param sub
	 *     - The subcommand to add.
	 */
	void addSubCommand(SubCommand sub)
	{
		subcommands.add(sub);
	}
}
