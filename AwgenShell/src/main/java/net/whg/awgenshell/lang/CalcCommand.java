package net.whg.awgenshell.lang;

import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.exec.CommandHandler;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.lang.equation.EquationSolver;
import net.whg.awgenshell.lang.equation.Val;
import net.whg.awgenshell.perms.PermissionNode;
import net.whg.awgenshell.util.CommandResult;

/**
 * Caclulates the value of a mathmatical formula given as an input. Supports
 * basic mathmatical functions: add, subtract, multiply, divide, raise to a
 * power, sqrt, sin, cos, tan, floor, ceil, and round.
 *
 * @author TheDudeFromCI
 */
public class CalcCommand implements CommandHandler
{
	private static final String[] ALIASES = {"eval"};
	private static final PermissionNode PERMS = new PermissionNode("lang.calc");
	private static EquationSolver solver = new EquationSolver();

	@Override
	public String getName()
	{
		return "calc";
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (!env.getCommandSender().getPermissions().hasPermission(PERMS))
		{
			env.getCommandSender().println("You do not have permission to use this command!");
			return CommandResult.ERROR;
		}

		if (args.length != 1)
		{
			env.getCommandSender().println("Unknown number of arguments!");
			return CommandResult.ERROR;
		}

		try
		{
			Val v = solver.parse(args[0].getValue());
			return new CommandResult(v.toString(), true, false);
		}
		catch (Exception exception)
		{
			env.getCommandSender().println("Failed to parse equation! " + exception.getMessage());
			return CommandResult.ERROR;
		}
	}

	@Override
	public String[] getAliases()
	{
		return ALIASES;
	}
}
