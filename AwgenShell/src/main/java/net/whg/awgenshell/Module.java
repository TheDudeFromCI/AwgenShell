package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;
import net.whg.awgenshell.lang.AppendCommand;
import net.whg.awgenshell.lang.ForCommand;
import net.whg.awgenshell.lang.FunctionCommand;
import net.whg.awgenshell.lang.IfCommand;
import net.whg.awgenshell.lang.PrintCommand;
import net.whg.awgenshell.lang.RandomCommand;
import net.whg.awgenshell.lang.SetCommand;
import net.whg.awgenshell.lang.WhileCommand;

/**
 * A module is a collection of commands which can be loaded together into a
 * shell environment.
 *
 * @author TheDudeFromCI
 */
public class Module
{
	static Module newLangModule()
	{
		Module m = new Module();

		m.loadCommand(new PrintCommand());
		m.loadCommand(new RandomCommand());
		m.loadCommand(new IfCommand());
		m.loadCommand(new SetCommand());
		m.loadCommand(new WhileCommand());
		m.loadCommand(new ForCommand());
		m.loadCommand(new AppendCommand());
		m.loadCommand(new FunctionCommand());

		return m;
	}

	private List<CommandHandler> commands = new ArrayList<>();

	/**
	 * Gets a command handler with the given name.
	 *
	 * @param name
	 *     - The name of the command.
	 * @return The command with the given name, or null if nothing is found.
	 */
	public CommandHandler getCommand(String name)
	{
		for (CommandHandler c : commands)
			if (c.getName().equalsIgnoreCase(name))
				return c;

		return null;
	}

	/**
	 * Gets a command handler with the given alias.
	 *
	 * @param alias
	 *     - The alias of the command.
	 * @return The command with the given alias, or null if nothing is found.
	 */
	public CommandHandler getCommandByAlias(String alias)
	{
		for (CommandHandler c : commands)
			for (String a : c.getAliases())
				if (a.equalsIgnoreCase(alias))
					return c;

		return null;
	}

	/**
	 * Loads a new command into this module.
	 *
	 * @param command
	 *     - The command.
	 */
	public void loadCommand(CommandHandler command)
	{
		if (command == null)
			return;

		if (commands.contains(command))
			return;

		commands.add(command);
	}
}
