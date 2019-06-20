package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a virtual environment to execute commands within.
 *
 * @author TheDudeFromCI
 */
public class ShellEnvironment
{
	private List<Variable> variables = new ArrayList<>();
	private List<Module> modules = new ArrayList<>();
	private CommandSender sender;

	/**
	 * Creates a new shell environment instance.
	 *
	 * @param sender
	 *     - The sender this environment is designed for.
	 */
	public ShellEnvironment(CommandSender sender)
	{
		this.sender = sender;

		loadModule(Module.newLangModule());
	}

	/**
	 * Gets or creates a variable within this environment.
	 *
	 * @param variable
	 *     - The name of the variable.
	 * @return The variable within this environment with the given name.
	 */
	public Variable getVariable(String variable)
	{
		for (Variable v : variables)
			if (v.getName().equals(variable))
				return v;

		Variable v = new Variable(variable);
		variables.add(v);
		return v;
	}

	/**
	 * Gets a list of all variables within this environment.
	 *
	 * @return A list of all variables.
	 */
	List<Variable> getVariables()
	{
		return variables;
	}

	/**
	 * Parses and runs a command input.
	 *
	 * @param line
	 *     - The input function to parse and run.
	 */
	public void runCommand(String line)
	{
		try
		{
			Input in = CommandParser.parse(this, line);
			in.execute(false);
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	/**
	 * Loads a command module to this shell environment.
	 *
	 * @param module
	 *     - The module to load.
	 */
	public void loadModule(Module module)
	{
		modules.add(module);
	}

	/**
	 * Gets the command handler from any of the loaded mod with the given name or
	 * alias.
	 *
	 * @param name
	 *     - The name or alias of the command.
	 * @return The command handler, or null if the command was not found.
	 */
	public CommandHandler getCommand(String name)
	{
		for (Module m : modules)
		{
			CommandHandler c = m.getCommand(name);

			if (c != null)
				return c;
		}

		return null;
	}

	/**
	 * Gets the command sender for this shell environment.
	 *
	 * @return
	 */
	public CommandSender getCommandSender()
	{
		return sender;
	}
}
