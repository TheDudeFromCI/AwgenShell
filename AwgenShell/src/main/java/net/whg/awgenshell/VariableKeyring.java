package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

public class VariableKeyring
{
	private List<Variable> variables = new ArrayList<>();

	public Variable getVariable(String name)
	{
		for (Variable var : variables)
			if (var.getName().equals(name))
				return var;
		return null;
	}

	public boolean hasVariable(String name)
	{
		return getVariable(name) != null;
	}

	public void addVariable(Variable var)
	{
		if (var == null)
			return;

		if (variables.contains(var))
			return;

		variables.add(var);
	}

	public List<Variable> getVariables()
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
