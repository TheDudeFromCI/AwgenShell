package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.CommandSender;

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
	public CommandResult execute(CommandSender sender, ArgumentValue[] args)
	{
		if (args.length < 2)
		{
			sender.println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		String seperator = "";
		int start = 0;

		String[] values = new String[args.length];
		for (int i = 0; i < args.length; i++)
			values[i] = args[i].getValue();

		if (values[0].equalsIgnoreCase("-s"))
		{
			if (args.length < 4)
			{
				sender.println("Unknown number of arguments!");
				return CommandResult.ERROR;
			}

			seperator = values[1];
			start = 2;
		}
		else if (values[0].equalsIgnoreCase("-n"))
		{
			if (args.length < 3)
			{
				sender.println("Unknown number of arguments!");
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
