package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.ShellEnvironment;

/**
 * This command allows an input string to be parsed and executed as a normal
 * command.
 *
 * @author TheDudeFromCI
 */
public class ExecCommand implements CommandHandler
{
	private static final String[] ALIASES =
	{
		"execute"
	};

	@Override
	public String getName()
	{
		return "exec";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (args.length != 1)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		return new CommandResult("", env.runCommand(args[0].getValue()), true);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
