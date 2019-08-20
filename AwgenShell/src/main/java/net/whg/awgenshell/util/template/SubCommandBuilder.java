package net.whg.awgenshell.util.template;

import java.util.LinkedList;
import java.util.List;
import net.whg.awgenshell.perms.PermissionNode;

/**
 * A builder class for quickly creating and configuring subcommands.
 *
 * @author TheDudeFromCI
 */
public class SubCommandBuilder
{
	private final CommandTemplateBuilder templateBuilder;
	private final String pattern;
	private final SubCommandExecutor executor;
	private PermissionNode permissionNode;
	private List<CommandFlagTemplate> flags = new LinkedList<>();

	public SubCommandBuilder(CommandTemplateBuilder templateBuilder, String pattern, SubCommandExecutor executor)
	{
		this.templateBuilder = templateBuilder;
		this.pattern = pattern;
		this.executor = executor;
	}

	public SubCommandBuilder permission(String permissionNode)
	{
		this.permissionNode = new PermissionNode(permissionNode);
		return this;
	}

	/**
	 * Adds a new potential flag to this subcommand. If user specifies this value
	 * with no value, the defaulyt value is used.
	 *
	 * @param flag
	 *     - The name of the flag, including a dash.
	 * @param numberValues
	 *     - The number of values this flag requires.
	 * @return This object.
	 */
	public SubCommandBuilder flag(String flag, int numberValues)
	{
		flags.add(new CommandFlagTemplate(flag, numberValues));
		return this;
	}

	/**
	 * Stops editing this subcommand and returns to editing the main command
	 * template builder.
	 * 
	 * @return The parent command template builder.
	 */
	public CommandTemplateBuilder finishSubCommand()
	{
		CommandFlagTemplate[] flagList = flags.toArray(new CommandFlagTemplate[flags.size()]);

		SubCommand sub = SubCommand.compile(pattern, executor, permissionNode, flagList);
		templateBuilder.addSubCommand(sub);
		return templateBuilder;
	}
}
