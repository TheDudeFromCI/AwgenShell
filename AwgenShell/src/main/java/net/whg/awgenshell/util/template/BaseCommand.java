package net.whg.awgenshell.util.template;

import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.exec.CommandHandler;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.util.CommandResult;

/**
 * A very simple implementation of a command which can be used to make command
 * templates to reduce much of the redundency of writing new commands.
 *
 * @author TheDudeFromCI
 */
public abstract class BaseCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	private final String name;
	private final CommandTemplate template;

	public BaseCommand(String name, CommandTemplate template)
	{
		this.name = name;
		this.template = template;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		String[] values = new String[args.length];
		for (int i = 0; i < args.length; i++)
			values[i] = args[i].getValue();

		SubCommand sub = template.getSubcommand(args, values);
		if (sub == null)
		{
			env.getCommandSender().println("Unknown subcommand!");
			return CommandResult.ERROR;
		}

		return sub.getExecutor().run(env, args, values);
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
