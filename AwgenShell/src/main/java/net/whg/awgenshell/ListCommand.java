package net.whg.awgenshell;

public class ListCommand implements CommandHandler
{
    private final String[] ALIAS = {};

    @Override
    public String getCommandName()
    {
        return "list";
    }

    @Override
    public String[] getCommandAliases()
    {
        return ALIAS;
    }

    @Override
    public String executeCommand(Command command)
    {
        StringBuilder sb = new StringBuilder();

        CommandArgument[] args = command.getArgs();
        for (int i = 0; i < args.length; i++)
        {
            if (i > 0)
                sb.append('\n');
            sb.append(args[i].getValue());
        }

        return sb.toString();
    }

    @Override
    public String getDescription()
    {
        return "Returns all arguments on a list seperated by new lines.";
    }

    @Override
    public String getHelpText()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("list [args]\n");
        sb.append(
                "  Returns all arguments in a list, with each argument on a new line.\n");

        return sb.toString();
    }
}
