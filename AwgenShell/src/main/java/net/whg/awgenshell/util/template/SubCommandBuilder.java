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
	private List<CommandFlag> flags = new LinkedList<>();

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
	 * @param defaultValue
	 *     - The default value of the flag.
	 * @return This object.
	 */
	public SubCommandBuilder flag(String flag, String defaultValue)
	{
		flags.add(new CommandFlag(flag, defaultValue));
		return this;
	}

	public CommandTemplateBuilder finishSubCommand()
	{
		CommandFlag[] flagList = flags.toArray(new CommandFlag[flags.size()]);

		SubCommand sub = SubCommand.compile(pattern, executor, permissionNode, flagList);
		templateBuilder.addSubCommand(sub);
		return templateBuilder;
	}
}
