package net.whg.awgenshell;

public class RandomCommand implements CommandHandler
{
    private final String[] ALIAS =
    {
            "rand"
    };

    @Override
    public String getCommandName()
    {
        return "random";
    }

    @Override
    public String[] getCommandAliases()
    {
        return ALIAS;
    }

    private boolean isInteger(String arg)
    {
        return arg.matches("[0-9]*");
    }

    private double asNumber(String arg)
    {
        if (arg.isEmpty())
            return 0;
        return Double.parseDouble(arg);
    }

    @Override
    public String executeCommand(Command command)
    {
        CommandArgument[] args = command.getArgs();
        CommandConsole console = command.getCommandSender().getConsole();

        if (args.length == 0)
            return String.valueOf(Math.random());

        if (args.length == 1)
        {
            String maxArg = args[0].getValue();

            try
            {
                double max = asNumber(maxArg);

                if (isInteger(maxArg))
                    return String.valueOf((int) (Math.random() * max));
                return String.valueOf(Math.random() * max);
            }
            catch (NumberFormatException exception2)
            {
                console.println("Unknown number '" + maxArg + "'");
                return "0";
            }
        }

        if (args.length == 2)
        {
            String minArg = args[0].getValue();
            String maxArg = args[1].getValue();

            double min, max;
            try
            {
                min = asNumber(minArg);
            }
            catch (NumberFormatException exception)
            {
                console.println("Unknown number '" + minArg + "'");
                return "0";
            }

            try
            {
                max = asNumber(maxArg);
            }
            catch (NumberFormatException exception)
            {
                console.println("Unknown number '" + maxArg + "'");
                return "0";
            }

            double random = Math.random() * (max - min) + min;
            if (isInteger(minArg) && isInteger(maxArg))
                return String.valueOf((int) random);
            return String.valueOf(random);
        }

        console.println("Unknown number of parameters!");
        return "";
    }

    @Override
    public String getDescription()
    {
        return "Returns a random number.";
    }

    @Override
    public String getHelpText()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("random\n");
        sb.append(
                "  Returns a random number between 0 (inclusive) and 1 (exclusive)\n");
        sb.append("random <max>\n");
        sb.append(
                "  Returns a random number between 0 (inclusive) and max (exclusive)\n");
        sb.append("random <min> <max>\n");
        sb.append(
                "  Returns a random number between min (inclusive) and max (exclusive)\n");

        return sb.toString();
    }
}
