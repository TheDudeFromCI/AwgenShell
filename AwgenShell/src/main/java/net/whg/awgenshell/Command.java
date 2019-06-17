package net.whg.awgenshell;

public class Command
{
    private String _name;
    private CommandArgument[] _args;
    private CommandSender _sender;

    public Command(String name, CommandArgument[] args, CommandSender sender)
    {
        _name = name;
        _args = args;
        _sender = sender;
    }

    public String getName()
    {
        return _name;
    }

    public CommandArgument[] getArgs()
    {
        return _args;
    }

    public void setArg(int index, CommandArgument value)
    {
        _args[index] = value;
    }

    public CommandArgument getArg(int index)
    {
        return _args[index];
    }

    public CommandSender getCommandSender()
    {
        return _sender;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append(_name);

        for (CommandArgument s : _args)
            sb.append(" ").append(s.getValue());

        return sb.toString();
    }
}
