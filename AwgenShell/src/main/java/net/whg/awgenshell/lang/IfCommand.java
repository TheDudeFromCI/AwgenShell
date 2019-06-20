package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.ShellEnvironment;
import net.whg.awgenshell.ShellUtils;

/**
 * The if command only executes a command if a given condition is met.
 * Additionally, "else if" and "else" clauses can be defined and used as well.
 * 
 * @author TheDudeFromCI
 */
public class IfCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	@Override
	public String getName()
	{
		return "if";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
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
