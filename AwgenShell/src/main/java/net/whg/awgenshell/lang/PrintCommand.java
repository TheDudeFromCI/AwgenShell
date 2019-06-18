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
			if (i > 0)
				line += " ";
			line += args[i].getValue();
		}

		sender.println(line);

		return "true";
	}
}
