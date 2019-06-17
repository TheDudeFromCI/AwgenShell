package net.whg.awgenshell;

public class HelpCommand implements CommandHandler
{
    private final String[] ALIAS = {};

    private CommandList _commandList;

    public HelpCommand(CommandList commandList)
    {
        _commandList = commandList;
    }

    @Override
    public String getCommandName()
    {
        return "help";
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

        if (args.length == 0)
        {
            for (int i = 0; i < _commandList.getCommandCount(); i++)
            {
                CommandHandler handler = _commandList.getCommand(i);

                console.println(handler.getCommandName() + "\n    "
                        + handler.getDescription());
            }

            return "";
        }

        if (args.length == 1)
        {
            String commandToFind = args[0].getValue();
            CommandHandler handler = _commandList.getCommand(commandToFind);

            if (handler == null)
            {
                console.println(
                        "Unable to find command '" + commandToFind + "'");
                return "";
            }

            console.println(handler.getHelpText());
            return "";
        }

        console.println("Unknown number of parameters!");
        return "";
    }

    @Override
    public String getDescription()
    {
        return "Gets the help text for a command, or a list of available commands.";
    }

    @Override
    public String getHelpText()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("help\n");
        sb.append("  Lists all available commands.\n");
        sb.append("help <command name>\n");
        sb.append("  Lists the help text for a given command.");

        return sb.toString();
    }
}
