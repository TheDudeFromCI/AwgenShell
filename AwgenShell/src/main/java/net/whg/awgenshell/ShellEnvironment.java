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
}
