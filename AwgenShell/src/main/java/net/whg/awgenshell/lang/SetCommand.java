package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.CommandSender;

public class SetCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	@Override
	public String getName()
	{
		return "set";
	}

	@Override
	public CommandResult execute(CommandSender sender, ArgumentValue[] args)
	{
		if (args.length != 1)
		{
			sender.println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		return new CommandResult(args[0].getValue(), true, false);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
