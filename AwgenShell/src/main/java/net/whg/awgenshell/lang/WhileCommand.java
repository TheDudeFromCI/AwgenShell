package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.CommandSender;
import net.whg.awgenshell.ShellUtils;

public class WhileCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	@Override
	public String getName()
	{
		return "while";
	}

	@Override
	public CommandResult execute(CommandSender sender, ArgumentValue[] args)
	{
		if (args.length != 3)
		{
			sender.println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		String doStatement = args[1].getValue();
		if (!doStatement.equalsIgnoreCase("do"))
		{
			sender.println("Unexpected code flow statement: '" + doStatement + "' at argument 1!");
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
