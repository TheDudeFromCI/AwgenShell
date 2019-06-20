package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.CommandSender;

public class RandomCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	@Override
	public String getName()
	{
		return "random";
	}

	private boolean isInteger(String num)
	{
		return num.matches("[0-9]+");
	}

	private boolean isFloat(String num)
	{
		return num.matches("[0-9]+\\.[0-9]*|[0-9]*\\.[0-9]+|[0-9]+");
	}

	@Override
	public CommandResult execute(CommandSender sender, ArgumentValue[] args)
	{
		if (args.length == 0)
			return new CommandResult(Math.random() + "", true, false);

		if (args.length == 1)
		{
			String max = args[0].getValue();

			if (isInteger(max))
				return new CommandResult((int) Math.round(Math.random() * Integer.valueOf(max)) + "", true, false);

			if (isFloat(max))
				return new CommandResult((float) (Math.random() * Float.valueOf(max)) + "", true, false);

			sender.println("Not a number: '" + max + "'!");
			return CommandResult.ERROR;
		}

		if (args.length == 2)
		{
			String min = args[0].getValue();
			String max = args[1].getValue();

			if (isInteger(min) && isInteger(max))
			{
				int maxI = Integer.valueOf(max);
				int minI = Integer.valueOf(min);

				return new CommandResult((int) Math.round(Math.random() * (maxI - minI)) + minI + "", true, false);
			}

			if (isFloat(min) && isFloat(max))
			{
				float maxI = Float.valueOf(max);
				float minI = Float.valueOf(min);

				return new CommandResult((float) (Math.random() * (maxI - minI)) + minI + "", true, false);
			}

			if (!isFloat(min))
				sender.println("Not a number: '" + min + "'!");

			if (!isFloat(max))
				sender.println("Not a number: '" + max + "'!");

			return CommandResult.ERROR;
		}

		sender.println("Unknown number of arguments!");
		return CommandResult.ERROR;
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
