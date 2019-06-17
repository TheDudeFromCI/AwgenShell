package net.whg.awgenshell;

import java.util.LinkedList;
import java.util.List;

public class CommandSet
{
	private List<CommandExecution> commands = new LinkedList<>();
	private VariableKeyring variables;

	public CommandSet(VariableKeyring variables)
	{
		this.variables = variables;
	}

	public void insertCommandExecution(CommandExecution execution)
	{
		if (execution == null)
			return;

		if (commands.contains(execution))
			return;

		commands.add(execution);
	}

	public Variable getVariable(String name)
	{
		if (name == null)
			return null;

		return variables.getVariable(name);
	}

	public Variable getOrCreateVariable(String name)
	{
		Variable var = getVariable(name);
		if (var != null)
			return var;

		var = new Variable(name);
		addVariable(var);
		return var;
	}

	public void addVariable(Variable variable)
	{
		if (variable == null)
			return;

		variables.addVariable(variable);
	}

	public List<CommandExecution> getCommandExecutions()
	{
		return commands;
	}

	public List<Variable> getVariables()
	{
		return variables.getVariables();
	}

	public void clear()
	{
		commands.clear();
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < commands.size(); i++)
		{
			CommandExecution exe = commands.get(i);
			sb.append("$").append(exe.getOutput().getName()).append(" = ").append(exe.getCommand().getName());
			for (CommandArgument arg : exe.getCommand().getArgs())
				sb.append(" ").append(arg);

			if (i < commands.size() - 1)
				sb.append('\n');
		}

		return sb.toString();
	}

	public int getVariableCount()
	{
		return variables.getVariables().size();
	}

	public int getCommandCount()
	{
		return commands.size();
	}

	public Variable getFinalOutput()
	{
		if (commands.size() == 0)
			return null;
		return commands.get(getCommandCount() - 1).getOutput();
	}
}
