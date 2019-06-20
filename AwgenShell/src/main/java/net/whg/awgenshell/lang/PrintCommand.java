package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandSender;

public class PrintCommand implements CommandHandler
{
	@Override
	public String getName()
	{
		return "print";
	}

	@Override
	public String execute(CommandSender sender, ArgumentValue[] args)
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

		return "true";
	}
}
