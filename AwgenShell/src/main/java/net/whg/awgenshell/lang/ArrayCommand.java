package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.PermissionNode;
import net.whg.awgenshell.ShellEnvironment;
import net.whg.awgenshell.ShellUtils;

/**
 * Returns the specified line within a given input, as if a 0-indexed array,
 * where each line was considered a new element. Values outside of the array
 * range return an empty string.
 *
 * @author TheDudeFromCI
 */
public class ArrayCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	private static final PermissionNode PERMS = new PermissionNode("lang.array");

	@Override
	public String getName()
	{
		return "array";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (!env.getCommandSender().getPermissions().hasPermission(PERMS))
		{
			env.getCommandSender().println("You do not have permission to use this command!");
			return CommandResult.ERROR;
		}

		if (args.length != 2)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		String a0 = args[0].getValue();
		String a1 = args[1].getValue();

		if (!ShellUtils.isInteger(a1))
		{
			env.getCommandSender().println("Not a number: '" + a1 + "'!");
			return CommandResult.ERROR;
		}

		int index = ShellUtils.asInt(a1);
		String[] lines = a0.split("\\r?\\n");

		String v;
		if (index < 0 || index >= lines.length)
			v = "";
		else
			v = lines[index];

		return new CommandResult(v, true, false);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
