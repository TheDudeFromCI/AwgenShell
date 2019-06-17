package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

public class VariableKeyring
{
	private List<CommandVariable> variables = new ArrayList<>();

	public CommandVariable getVariable(String name)
	{
		for (CommandVariable var : variables)
			if (var.getName().equals(name))
				return var;
		return null;
	}

	public boolean hasVariable(String name)
	{
		return getVariable(name) != null;
	}

	public void addVariable(CommandVariable var)
	{
		if (var == null)
			return;

		if (variables.contains(var))
			return;

		variables.add(var);
	}

	public List<CommandVariable> getVariables()
	{
		return variables;
	}

	public void clearTemp()
	{
		for (int i = 0; i < variables.size();)
		{
			if (variables.get(i).getName().matches("[0-9]*"))
				variables.remove(i);
			else
				i++;
		}
	}
}
