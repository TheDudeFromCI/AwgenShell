package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.CommandSender;

public class PrintCommand implements CommandHandler
{
	private static final String[] ALIASES =
	{
		"echo", "say"
	};

	@Override
	public String getName()
	{
		return "print";
	}

	@Override
	public CommandResult execute(CommandSender sender, ArgumentValue[] args)
	{
		String line = "";

		for (int i = 0; i < args.length; i++)
		{
			String a = args[i].getValue();

			if (i > 0 && a.matches("[a-zA-z0-9].*"))
				line += " ";

			line += a;
		}

		sender.println(line);

		return new CommandResult("", true, true);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
