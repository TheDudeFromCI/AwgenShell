package net.whg.awgenshell.exec;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.whg.awgenshell.arg.Variable;
import net.whg.awgenshell.parse.CommandParseException;
import net.whg.awgenshell.parse.CommandParser;
import net.whg.awgenshell.parse.Input;

/**
 * This class represents a virtual environment to execute commands within.
 *
 * @author TheDudeFromCI
 */
public class ShellEnvironment
{
	private static final Logger logger = LoggerFactory.getLogger(ShellEnvironment.class);

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
	public List<Variable> getVariables()
	{
		return variables;
	}

	/**
	 * Parses and runs a command input.
	 *
	 * @param line
	 *     - The input function to parse and run.
	 * @return True if the command was successfully parsed, false otherwise.
	 */
	public boolean runCommand(String line)
	{
		try
		{
			runCommandNoisy(line);
			return true;
		}
		catch (CommandParseException exception)
		{
			logger.error("Failed to parse command!", exception);
			return false;
		}
		catch (Exception exception)
		{
			logger.error("Unexcepted error occured while running command!", exception);
			return false;
		}
	}

	/**
	 * Parses and runs a command input. This method is identicle to
	 * {@link #runCommand(String)} with the difference that this method will not
	 * capture any errors and will send them out.
	 *
	 * @param line
	 *     - The input function to parse and run.
	 * @throws CommandParseException
	 *     - If the command could not be parsed.
	 */
	public void runCommandNoisy(String line)
	{
		Input in = CommandParser.parse(this, line);
		in.execute(false);
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

		for (Module m : modules)
		{
			CommandHandler c = m.getCommandByAlias(name);

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
