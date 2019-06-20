package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.ShellEnvironment;
import net.whg.awgenshell.ShellUtils;

/**
 * Preforms a command continuously while the given condition returns true.
 * 
 * @author TheDudeFromCI
 */
public class WhileCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	@Override
	public String getName()
	{
		return "while";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (args.length != 3)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		String doStatement = args[1].getValue();
		if (!doStatement.equalsIgnoreCase("do"))
		{
			env.getCommandSender().println("Unexpected code flow statement: '" + doStatement + "' at argument 1!");
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
