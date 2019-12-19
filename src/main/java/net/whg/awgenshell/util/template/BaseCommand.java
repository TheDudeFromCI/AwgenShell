package net.whg.awgenshell.util.template;

import java.util.ArrayList;
import java.util.List;
import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.exec.CommandHandler;
import net.whg.awgenshell.exec.CommandSender;
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
		CommandSender sender = env.getCommandSender();

		List<InputArgument> parameters = new ArrayList<>();
		for (ArgumentValue a : args)
			parameters.add(new InputArgument(a));

		SubCommand sub = template.getSubcommand(parameters);
		if (sub == null)
		{
			env.getCommandSender().println("Unknown subcommand for " + template.getName() + "!");
			return CommandResult.ERROR;
		}

		if (!checkPerms(sender, sub))
			return CommandResult.ERROR;

		List<String> unparsedFlags = findFlagArguments(args, parameters);
		CommandFlag[] flags = parseFlags(unparsedFlags, sub, sender);
		if (flags == null)
			return CommandResult.ERROR;

		return sub.getExecutor().run(env, parameters.toArray(new InputArgument[parameters.size()]), flags);
	}

	private boolean checkPerms(CommandSender sender, SubCommand sub)
	{
		PermissionNode perms = sub.getPermissions();
		if (perms == null)
			perms = template.getPermissions();

		if (sender.getPermissions().hasPermission(perms))
			return true;

		sender.println("You do not have permission to preform this action!");
		return false;
	}

	private List<String> findFlagArguments(ArgumentValue[] args, List<InputArgument> parametersList)
	{
		List<String> flags = new ArrayList<>();

		int j = 0;
		for (ArgumentValue arg : args)
		{
			if (parametersList.get(j).getArgument() == arg)
				j++;
			else
				flags.add(arg.getValue());
		}

		return flags;
	}

	private CommandFlag[] parseFlags(List<String> unparsedFlags, SubCommand sub, CommandSender sender)
	{
		List<CommandFlag> flags = new ArrayList<>();
		for (int i = 0; i < unparsedFlags.size(); i++)
		{
			CommandFlagTemplate baseFlag = getFlag(sub, unparsedFlags.get(i), sender);
			if (baseFlag == null)
				return null;

			String name = unparsedFlags.get(i);
			String[] values = new String[baseFlag.getNumberOfValues()];

			for (int j = 0; j < values.length; j++)
				values[j] = unparsedFlags.remove(i + j + 1);

			flags.add(new CommandFlag(name, values));
		}

		return flags.toArray(new CommandFlag[flags.size()]);
	}

	private CommandFlagTemplate getFlag(SubCommand sub, String flag, CommandSender sender)
	{
		for (CommandFlagTemplate f : sub.getFlags())
			if (f.getName().equals(flag))
				return f;

		sender.println("Unknown flag! '" + flag + "'");
		return null;
	}

	@Override
	public String[] getAliases()
	{
		return template.getAliases();
	}
}
