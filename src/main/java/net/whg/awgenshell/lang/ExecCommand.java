package net.whg.awgenshell.lang;

import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.exec.CommandHandler;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.PermissionNode;
import net.whg.awgenshell.util.CommandResult;

/**
 * This command allows an input string to be parsed and executed as a normal
 * command.
 *
 * @author TheDudeFromCI
 */
public class ExecCommand implements CommandHandler
{
	private static final String[] ALIASES =
	{
		"execute"
	};

	private static final PermissionNode PERMS = new PermissionNode("lang.exec");

	@Override
	public String getName()
	{
		return "exec";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (!env.getCommandSender().getPermissions().hasPermission(PERMS))
		{
			env.getCommandSender().println("You do not have permission to use this command!");
			return CommandResult.ERROR;
		}

		if (args.length != 1)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		return new CommandResult("", env.runCommand(args[0].getValue()), true);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
