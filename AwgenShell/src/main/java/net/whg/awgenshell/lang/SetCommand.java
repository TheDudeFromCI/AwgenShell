package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.PermissionNode;
import net.whg.awgenshell.ShellEnvironment;

/**
 * This class is simple a pass through command which returns the string value of
 * the argument it is provided.
 *
 * @author TheDudeFromCI
 */
public class SetCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	private static final PermissionNode PERMS = new PermissionNode("lang.set");

	@Override
	public String getName()
	{
		return "set";
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

		return new CommandResult(args[0].getValue(), true, false);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
