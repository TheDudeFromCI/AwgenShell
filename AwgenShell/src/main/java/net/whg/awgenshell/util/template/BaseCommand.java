package net.whg.awgenshell.util.template;

import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.exec.CommandHandler;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.PermissionNode;
import net.whg.awgenshell.util.CommandResult;

/**
 * A very simple implementation of a command which can be used to make command
 * templates to reduce much of the redundency of writing new commands.
 *
 * @author TheDudeFromCI
 */
public abstract class BaseCommand implements CommandHandler
{
	private final CommandTemplate template;

	public BaseCommand(CommandTemplate template)
	{
		this.template = template;
	}

	@Override
	public String getName()
	{
		return template.getName();
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		String[] values = new String[args.length];
		for (int i = 0; i < args.length; i++)
			values[i] = args[i].getValue();

		InputArgument[] parameters = new InputArgument[args.length];
		for (int i = 0; i < parameters.length; i++)
			parameters[i] = new InputArgument(args[i]);

		SubCommand sub = template.getSubcommand(parameters);
		if (sub == null)
		{
			env.getCommandSender().println("Unknown subcommand!");
			return CommandResult.ERROR;
		}

		PermissionNode perms = sub.getPermissions();
		if (perms == null)
			perms = template.getPermissions();

		if (!env.getCommandSender().getPermissions().hasPermission(perms))
		{
			env.getCommandSender().println("You do not have permission to preform this action!");
			return CommandResult.ERROR;
		}

		return sub.getExecutor().run(env, parameters);
	}

	@Override
	public String[] getAliases()
	{
		return template.getAliases();
	}
}
