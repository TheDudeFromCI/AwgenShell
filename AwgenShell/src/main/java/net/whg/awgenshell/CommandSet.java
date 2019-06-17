package net.whg.awgenshell;

import java.util.LinkedList;
import java.util.List;

public class CommandSet
{
    private List<CommandExecution> _commands = new LinkedList<>();
    private VariableKeyring _variables;

    public CommandSet(VariableKeyring variables)
    {
        _variables = variables;
    }

    public void insertCommandExecution(CommandExecution execution)
    {
        if (execution == null)
            return;

        if (_commands.contains(execution))
            return;

        _commands.add(execution);
    }

    public CommandVariable getVariable(String name)
    {
        if (name == null)
            return null;

        return _variables.getVariable(name);
    }

    public CommandVariable getOrCreateVariable(String name)
    {
        CommandVariable var = getVariable(name);
        if (var != null)
            return var;

        var = new CommandVariable(name);
        addVariable(var);
        return var;
    }

    public void addVariable(CommandVariable variable)
    {
        if (variable == null)
            return;

        _variables.addVariable(variable);
    }

    public List<CommandExecution> getCommandExecutions()
    {
        return _commands;
    }

    public List<CommandVariable> getVariables()
    {
        return _variables.getVariables();
    }

    public void clear()
    {
        _commands.clear();
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < _commands.size(); i++)
        {
            CommandExecution exe = _commands.get(i);
            sb.append("$").append(exe.getOutput().getName()).append(" = ")
                    .append(exe.getCommand().getName());
            for (CommandArgument arg : exe.getCommand().getArgs())
                sb.append(" ").append(arg);

            if (i < _commands.size() - 1)
                sb.append('\n');
        }

        return sb.toString();
    }

    public int getVariableCount()
    {
        return _variables.getVariables().size();
    }

    public int getCommandCount()
    {
        return _commands.size();
    }

    public CommandVariable getFinalOutput()
    {
        if (_commands.size() == 0)
            return null;
        return _commands.get(getCommandCount() - 1).getOutput();
    }
}
