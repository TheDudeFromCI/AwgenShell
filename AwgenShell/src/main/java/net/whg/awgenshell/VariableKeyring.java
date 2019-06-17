package net.whg.awgenshell;

import java.util.ArrayList;
import java.util.List;

public class VariableKeyring
{
	private List<CommandVariable> _variables = new ArrayList<>();

	public CommandVariable getVariable(String name)
	{
		for (CommandVariable var : _variables)
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

		if (_variables.contains(var))
			return;

		_variables.add(var);
	}

	public List<CommandVariable> getVariables()
	{
		return _variables;
	}

	public void clearTemp()
	{
		for (int i = 0; i < _variables.size();)
		{
			if (_variables.get(i).getName().matches("[0-9]*"))
				_variables.remove(i);
			else
				i++;
		}
	}
}
