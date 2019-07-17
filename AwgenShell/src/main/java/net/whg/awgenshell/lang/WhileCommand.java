package net.whg.awgenshell.lang;

import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.exec.CommandHandler;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.PermissionNode;
import net.whg.awgenshell.util.CommandResult;
import net.whg.awgenshell.util.ShellUtils;

/**
 * Preforms a command continuously while the given condition returns true.
 *
 * @author TheDudeFromCI
 */
public class WhileCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	private static final PermissionNode PERMS = new PermissionNode("lang.while");

	@Override
	public String getName()
	{
		return "while";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (!env.getCommandSender().getPermissions().hasPermission(PERMS))
		{
			env.getCommandSender().println("You do not have permission to use this command!");
			return CommandResult.ERROR;
		}

		if (args.length != 3)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		String doStatement = args[1].getValue();
		if (!doStatement.equalsIgnoreCase("do"))
		{
			env.getCommandSender().println("Unexpected code flow statement: '" + doStatement + "' at argument 1!");
			return CommandResult.ERROR;
		}

		boolean called = false;
		String lastVal = "";
		while (ShellUtils.stringToBoolean(args[0].getValue()))
		{
			lastVal = args[2].getValue();
			called = true;
		}

		return new CommandResult(lastVal, called, true);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
