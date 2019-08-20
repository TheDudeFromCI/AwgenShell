package net.whg.awgenshell.lang;

import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.exec.CommandHandler;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.PermissionNode;
import net.whg.awgenshell.util.CommandResult;
import net.whg.awgenshell.util.ShellUtils;

/**
 * The if command only executes a command if a given condition is met.
 * Additionally, "else if" and "else" clauses can be defined and used as well.
 *
 * @author TheDudeFromCI
 */
public class IfCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	private static final PermissionNode PERMS = new PermissionNode("lang.if");

	@Override
	public String getName()
	{
		return "if";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (!env.getCommandSender().getPermissions().hasPermission(PERMS))
		{
			env.getCommandSender().println("You do not have permission to use this command!");
			return CommandResult.ERROR;
		}

		if (args.length < 3 || (args.length - 3) % 2 != 0)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		for (int i = 1; i < args.length; i += 2)
		{
			String a = args[i].getValue();

			if (a.equalsIgnoreCase("then") && (i - 1) % 4 == 0)
				continue;
			if (a.equalsIgnoreCase("elseif") && (i - 3) % 4 == 0)
				continue;
			if (a.equalsIgnoreCase("else") && i == args.length - 2)
				continue;

			env.getCommandSender().println("Unexpected code flow statement: '" + a + "' at argument " + i + "!");
			return new CommandResult("", true, true);
		}

		if (ShellUtils.stringToBoolean(args[0].getValue()))
			return new CommandResult(args[2].getValue(), true, true);

		for (int i = 3; i < args.length; i += 4)
		{
			if (args[i].getValue().equalsIgnoreCase("elseif"))
			{
				if (ShellUtils.stringToBoolean(args[i + 1].getValue()))
					return new CommandResult(args[i + 3].getValue(), true, true);
			}
			else
				return new CommandResult(args[i + 1].getValue(), true, true);
		}

		return new CommandResult("", false, true);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
