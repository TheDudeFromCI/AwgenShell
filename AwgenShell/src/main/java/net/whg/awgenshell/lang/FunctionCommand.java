package net.whg.awgenshell.lang;

import java.util.HashMap;
import java.util.Map;
import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.ShellEnvironment;

/**
 * This powerful command allows commands to be named and saved for execution at
 * a later time. Functions are stored in this instance of the function command
 * class but bound to the shell environment they were initalized from.
 *
 * @author TheDudeFromCI
 */
public class FunctionCommand implements CommandHandler
{
	private static final String[] ALIASES =
	{
		"func", "def", "run"
	};

	private Map<ShellEnvironment, Map<String, ArgumentValue>> functions = new HashMap<>();

	@Override
	public String getName()
	{
		return "function";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (args.length == 2)
		{
			Map<String, ArgumentValue> f = functions.get(env);

			if (f == null)
				functions.put(env, f = new HashMap<>());

			String a = args[0].getValue();

			f.put(a, args[1]);
			return new CommandResult(a, true, true);
		}

		if (args.length == 1)
		{
			Map<String, ArgumentValue> f = functions.get(env);

			if (f == null)
				functions.put(env, f = new HashMap<>());

			String funcName = args[0].getValue();

			if (!f.containsKey(funcName))
			{
				env.getCommandSender().println("Unknown function: '" + funcName + "'!");
				return CommandResult.ERROR;
			}

			ArgumentValue func = f.get(funcName);
			return new CommandResult(func.getValue(), true, true);
		}

		env.getCommandSender().println("Unknown number of parameters!");
		return CommandResult.ERROR;
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}

}
