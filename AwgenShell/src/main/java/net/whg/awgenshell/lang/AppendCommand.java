package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.ShellEnvironment;

/**
 * Adds two or more strings together, using an optional seperator string. Can
 * use the "-s [seperator]" flag to assign the string seperator. the "-n" flag
 * can be used to set newline characters as the string seperator.
 *
 * @author TheDudeFromCI
 */
public class AppendCommand implements CommandHandler
{
	private static final String[] ALIASES =
	{
		"add", "concat"
	};

	@Override
	public String getName()
	{
		return "append";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (args.length < 1)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		String seperator = "";
		int start = 0;

		String[] values = new String[args.length];
		for (int i = 0; i < args.length; i++)
			values[i] = args[i].getValue();

		if (values[0].equalsIgnoreCase("-s"))
		{
			if (args.length < 3)
			{
				env.getCommandSender().println("Unknown number of arguments!");
				return CommandResult.ERROR;
			}

			seperator = values[1];
			start = 2;
		}
		else if (values[0].equalsIgnoreCase("-n"))
		{
			if (args.length < 2)
			{
				env.getCommandSender().println("Unknown number of arguments!");
				return CommandResult.ERROR;
			}

			seperator = "\n";
			start = 1;
		}

		String line = "";
		for (int i = start; i < values.length; i++)
		{
			if (i > start && !seperator.isEmpty())
				line += seperator;

			line += values[i];
		}

		return new CommandResult(line, true, false);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
