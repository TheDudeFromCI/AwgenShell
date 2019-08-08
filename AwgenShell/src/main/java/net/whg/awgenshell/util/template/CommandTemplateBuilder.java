package net.whg.awgenshell.util.template;

import java.util.LinkedList;
import java.util.List;

/**
 * This class acts as a builder for creating new command templates.
 *
 * @author TheDudeFromCI
 */
public class CommandTemplateBuilder
{
	private List<SubCommand> subcommands = new LinkedList<>();

	public CommandTemplateBuilder subCommand(String pattern, SubCommandExecutor exector)
	{
		subcommands.add(SubCommand.compile(pattern, exector));
		return this;
	}

	public CommandTemplate build()
	{
		return new CommandTemplate(subcommands.toArray(new SubCommand[subcommands.size()]));
	}
}
