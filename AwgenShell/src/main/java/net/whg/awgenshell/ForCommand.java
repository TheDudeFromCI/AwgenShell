package net.whg.awgenshell;

public class ForCommand implements CommandHandler
{
    private String[] ALIAS =
    {
            "foreach"
    };

    @Override
    public String getCommandName()
    {
        return "for";
    }

    @Override
    public String[] getCommandAliases()
    {
        return ALIAS;
    }

    @Override
    public String getDescription()
    {
        return "Runs a command multiple times.";
    }

    @Override
    public String getHelpText()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("for <var> <min> <max> [step] do <command>\n");
        sb.append(
                "  For each value between <min> (inclusive) and max (exclusive), assigns <var>\n"
                        + "  to that integer value and then executes <command>. The command should be a\n"
                        + "  dynamic variable. <step> is the step size and is optional. Defaults to 1.\n");
        sb.append("for <var> <list> do <command>\n");
        sb.append(
                "  For each line in the <list>, assigns <var> to that value and then executes\n"
                        + "  'command.' Command should be a dynamic variable.\n");

        return sb.toString();
    }

    private int asNumber(String arg)
    {
        if (arg.isEmpty())
            return 0;
        return Integer.parseInt(arg);
    }

    @Override
    public String executeCommand(Command command)
    {
        CommandArgument[] args = command.getArgs();
        CommandConsole console = command.getCommandSender().getConsole();

        if (args.length == 5)
        {
            if (!(args[0] instanceof VariableArgument))
            {
                console.println(args[0].getValue() + " is not a variable!");
                return "";
            }

            if (!(args[3] instanceof StringArgument)
                    && !args[3].getValue().equals("do"))
            {
                console.println(
                        "Parameter 'do' must be placed before command!");
                return "";
            }

            if (!(args[4] instanceof DynamicVariableArgument))
            {
                console.println(args[4].getValue() + " is not a command!");
                return "";
            }

            VariableArgument var = (VariableArgument) args[0];
            DynamicVariableArgument com = (DynamicVariableArgument) args[4];

            String minArg = args[1].getValue();
            String maxArg = args[2].getValue();

            int min, max;
            try
            {
                min = asNumber(minArg);
            }
            catch (NumberFormatException exception)
            {
                console.println("Unknown number '" + minArg + "'");
                return "";
            }

            try
            {
                max = asNumber(maxArg);
            }
            catch (NumberFormatException exception)
            {
                console.println("Unknown number '" + maxArg + "'");
                return "";
            }

            for (int i = min; i < max; i++)
            {
                var.setValue(String.valueOf(i));
                com.getValue();
            }

            return "";
        }

        if (args.length == 6)
        {
            if (!(args[0] instanceof VariableArgument))
            {
                console.println(args[0].getValue() + " is not a variable!");
                return "";
            }

            if (!(args[4] instanceof StringArgument)
                    && !args[4].getValue().equals("do"))
            {
                console.println(
                        "Parameter 'do' must be placed before command!");
                return "";
            }

            if (!(args[5] instanceof DynamicVariableArgument))
            {
                console.println(args[5].getValue() + " is not a command!");
                return "";
            }

            VariableArgument var = (VariableArgument) args[0];
            DynamicVariableArgument com = (DynamicVariableArgument) args[5];

            String minArg = args[1].getValue();
            String maxArg = args[2].getValue();
            String stepArg = args[3].getValue();

            int min, max, step;
            try
            {
                min = asNumber(minArg);
            }
            catch (NumberFormatException exception)
            {
                console.println("Unknown number '" + minArg + "'");
                return "";
            }

            try
            {
                max = asNumber(maxArg);
            }
            catch (NumberFormatException exception)
            {
                console.println("Unknown number '" + maxArg + "'");
                return "";
            }

            try
            {
                step = asNumber(stepArg);
            }
            catch (NumberFormatException exception)
            {
                console.println("Unknown number '" + stepArg + "'");
                return "";
            }

            for (int i = min; i < max; i += step)
            {
                var.setValue(String.valueOf(i));
                com.getValue();
            }

            return "";
        }

        if (args.length == 4)
        {
            if (!(args[0] instanceof VariableArgument))
            {
                console.println(args[0].getValue() + " is not a variable!");
                return "";
            }

            if (!(args[2] instanceof StringArgument)
                    && !args[2].getValue().equals("do"))
            {
                console.println(
                        "Parameter 'do' must be placed before command!");
                return "";
            }

            if (!(args[3] instanceof DynamicVariableArgument))
            {
                console.println(args[3].getValue() + " is not a command!");
                return "";
            }

            VariableArgument var = (VariableArgument) args[0];
            DynamicVariableArgument com = (DynamicVariableArgument) args[3];

            String[] list = args[1].getValue().split("\n");

            for (int i = 0; i < list.length; i++)
            {
                var.setValue(list[i]);
                com.getValue();
            }

            return "";
        }

        console.println("Unknown number of parameters!");
        return "";
    }
}
