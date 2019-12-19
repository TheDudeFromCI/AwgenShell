package net.whg.awgenshell.lang;

import static net.whg.awgenshell.util.ShellUtils.asFloat;
import static net.whg.awgenshell.util.ShellUtils.asInt;
import static net.whg.awgenshell.util.ShellUtils.isFloat;
import static net.whg.awgenshell.util.ShellUtils.isInteger;
import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.exec.CommandHandler;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.PermissionNode;
import net.whg.awgenshell.util.CommandResult;

/**
 * Generates random numbers. If no inputs are given, returns a random number
 * between zero (inclusive) and one (exclusive). If a single input value is
 * given, then a random number between zero and the given input is returned. If
 * two values are provided, then a value between those two is provided. The type
 * of value returned is based on the value type of the inputs. If all inputs are
 * integers, then the output is an integer. Otherwise, the output is a float.
 *
 * @author TheDudeFromCI
 */
public class RandomCommand implements CommandHandler
{
	private static final String[] ALIASES = {};

	private static final PermissionNode PERMS = new PermissionNode("lang.random");

	@Override
	public String getName()
	{
		return "random";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (!env.getCommandSender().getPermissions().hasPermission(PERMS))
		{
			env.getCommandSender().println("You do not have permission to use this command!");
			return CommandResult.ERROR;
		}

		if (args.length == 0)
			return new CommandResult(Math.random() + "", true, false);

		if (args.length == 1)
		{
			String max = args[0].getValue();

			if (isInteger(max))
				return new CommandResult((int) Math.round(Math.random() * Integer.valueOf(max)) + "", true, false);

			if (isFloat(max))
				return new CommandResult((float) (Math.random() * Float.valueOf(max)) + "", true, false);

			env.getCommandSender().println("Not a number: '" + max + "'!");
			return CommandResult.ERROR;
		}

		if (args.length == 2)
		{
			String min = args[0].getValue();
			String max = args[1].getValue();

			if (isInteger(min) && isInteger(max))
			{
				int maxI = asInt(max);
				int minI = asInt(min);

				return new CommandResult((int) Math.round(Math.random() * (maxI - minI)) + minI + "", true, false);
			}

			if (isFloat(min) && isFloat(max))
			{
				float maxI = asFloat(max);
				float minI = asFloat(min);

				return new CommandResult((float) (Math.random() * (maxI - minI)) + minI + "", true, false);
			}

			if (!isFloat(min))
				env.getCommandSender().println("Not a number: '" + min + "'!");

			if (!isFloat(max))
				env.getCommandSender().println("Not a number: '" + max + "'!");

			return CommandResult.ERROR;
		}

		env.getCommandSender().println("Unknown number of arguments!");
		return CommandResult.ERROR;
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
