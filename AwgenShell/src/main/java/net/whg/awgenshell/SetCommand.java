package net.whg.awgenshell;

public class SetCommand implements CommandHandler
{
    private final String[] ALIAS = {};

    @Override
    public String getCommandName()
    {
        return "set";
    }

    @Override
    public String[] getCommandAliases()
    {
        return ALIAS;
    }

    @Override
    public String executeCommand(Command command)
    {
        CommandArgument[] args = command.getArgs();
        CommandConsole console = command.getCommandSender().getConsole();

        if (args.length != 1)
        {
            console.println("Unknown number of parameters!");
            return "";
        }

        return args[0].getValue();
    }

    @Override
    public String getDescription()
    {
        return "Returns the input argument. Used for variable assignment.";
    }

    @Override
    public String getHelpText()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("set <arg>\n");
        sb.append("  Returns the input argument.\n");

        return sb.toString();
    }
}
