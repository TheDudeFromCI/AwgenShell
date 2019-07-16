package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.PermissionNode;
import net.whg.awgenshell.ShellEnvironment;

/**
 * The print command adds all inputs together into a string and sends that
 * message to the user. If multiple arguments are provided, they are added
 * together. If an argument starts with a letter or number, then a space is
 * automatically added between the arguments.
 *
 * @author TheDudeFromCI
 */
public class PrintCommand implements CommandHandler
{
	private static final String[] ALIASES =
	{
		"echo", "say"
	};

	private static final PermissionNode PERMS = new PermissionNode("lang.print");

	@Override
	public String getName()
	{
		return "print";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (!env.getCommandSender().getPermissions().hasPermission(PERMS))
		{
			env.getCommandSender().println("You do not have permission to use this command!");
			return CommandResult.ERROR;
		}

		String line = "";
		for (int i = 0; i < args.length; i++)
		{
			String a = args[i].getValue();

			if (i > 0 && !line.endsWith(" ") && a.matches("[a-zA-z0-9].*"))
				line += " ";

			line += a;
		}

		env.getCommandSender().println(line);

		return new CommandResult("", true, true);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
