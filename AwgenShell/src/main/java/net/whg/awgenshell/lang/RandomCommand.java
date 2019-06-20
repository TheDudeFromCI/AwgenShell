package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
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
	public String execute(CommandSender sender, ArgumentValue[] args)
	{
		if (args.length == 0)
			return Math.random() + "";

		if (args.length == 1)
		{
			String max = args[0].getValue();

			if (isInteger(max))
				return (int) Math.round(Math.random() * Integer.valueOf(max)) + "";

			if (isFloat(max))
				return (float) (Math.random() * Float.valueOf(max)) + "";

			sender.println("Not a number: '" + max + "'!");
			return "false";
		}

		if (args.length == 2)
		{
			String min = args[0].getValue();
			String max = args[1].getValue();

			if (isInteger(min) && isInteger(max))
			{
				int maxI = Integer.valueOf(max);
				int minI = Integer.valueOf(min);

				return (int) (Math.random() * (maxI - minI)) + minI + "";
			}

			if (isFloat(min) && isFloat(max))
			{
				float maxI = Float.valueOf(max);
				float minI = Float.valueOf(min);

				return (float) (Math.random() * (maxI - minI)) + minI + "";
			}

			if (!isFloat(min))
				sender.println("Not a number: '" + min + "'!");

			if (!isFloat(max))
				sender.println("Not a number: '" + max + "'!");

			return "false";
		}

		sender.println("Unknown number of arguments!");
		return "false";
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
