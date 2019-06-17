package net.whg.awgenshell;

public class CommandExecution
{
    private Command _command;
    private CommandVariable _output;

    public CommandExecution(Command command, CommandVariable output)
    {
        _command = command;
        _output = output;
    }

    public Command getCommand()
    {
        return _command;
    }

    public CommandVariable getOutput()
    {
        return _output;
    }
}
