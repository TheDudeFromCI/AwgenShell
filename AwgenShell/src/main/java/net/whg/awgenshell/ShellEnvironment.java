package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a virtual environment to execute commands within.
 *
 * @author TheDudeFromCI
 */
public class ShellEnvironment
{
	private List<Variable> variables = new ArrayList<>();
	private List<Variable> tempVars = new LinkedList<>();
	private List<Module> modules = new ArrayList<>();

	public Variable getVariable(String variable)
	{
		for (Variable v : variables)
			if (v.getName().equals(variable))
				return v;

		Variable v = new Variable(variable);
		variables.add(v);
		return v;
	}

	public List<Variable> getVariables()
	{
		return variables;
	}

	public Variable getTempVariable()
	{
		Variable v = new Variable(tempVars.size() + "");
		tempVars.add(v);

		return v;
	}

	public void clearTempVars()
	{
		tempVars.clear();
	}

	public void runCommand(String line)
	{
		try
		{
			Input in = CommandParser.parse(this, line);
			System.out.println(in.execute(this).getValue());
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public void loadModule(Module module)
	{
		modules.add(module);
	}

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
}
