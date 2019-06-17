package net.whg.awgenshell;

public class PrintCommand implements CommandHandler
{
	private final String[] ALIAS =
	{
		"echo", "say"
	};

	@Override
	public String getCommandName()
	{
		return "print";
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

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < args.length; i++)
		{
			if (i > 0)
				sb.append(' ');
			sb.append(args[i].getValue());
		}
		String out = sb.toString();
		console.println(out);

		return out;
	}

	@Override
	public String getDescription()
	{
		return "Prints the input arguments, seperated by spaces.";
	}

	@Override
	public String getHelpText()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("print <args>\n");
		sb.append("  Prints the input arguments(s), seperated by spaces.\n");

		return sb.toString();
	}
}
