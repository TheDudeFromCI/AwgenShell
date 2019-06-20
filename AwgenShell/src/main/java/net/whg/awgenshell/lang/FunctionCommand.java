package net.whg.awgenshell.lang;

import java.util.HashMap;
import java.util.Map;
import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.CommandSender;

public class FunctionCommand implements CommandHandler
{
	private static final String[] ALIASES =
	{
		"func", "def"
	};

	private Map<String, ArgumentValue> functions = new HashMap<>();

	@Override
	public String getName()
	{
		return "function";
	}

	@Override
	public CommandResult execute(CommandSender sender, ArgumentValue[] args)
	{
		if (args.length == 2)
		{
			String a = args[0].getValue();

			functions.put(a, args[1]);
			return new CommandResult(a, true, true);
		}

		if (args.length == 1)
		{
			String funcName = args[0].getValue();

			if (!functions.containsKey(funcName))
			{
				sender.println("Unknown function: '" + funcName + "'!");
				return CommandResult.ERROR;
			}

			ArgumentValue func = functions.get(funcName);
			return new CommandResult(func.getValue(), true, true);
		}

		sender.println("Unknown number of parameters!");
		return CommandResult.ERROR;
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}

}
