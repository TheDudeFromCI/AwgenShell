package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.ShellEnvironment;
import net.whg.awgenshell.Variable;
import net.whg.awgenshell.VariableArgument;

/**
 * Loops through a series of integer values or elements in a list. For integer
 * values, the loop starts a specific value, and walks along a defined step size
 * until surpasing the given end value, the given variable is assigned the
 * integer value and the given command is executed for each step along the way.
 * Looping through a list is similar except that each line in the given list is
 * assign to the variable instead of an integer.
 *
 * @author TheDudeFromCI
 */
public class ForCommand implements CommandHandler
{
	private static final String[] ALIAS =
	{
		"foreach"
	};

	@Override
	public String getName()
	{
		return "for";
	}

	private boolean isInteger(String num)
	{
		return num.matches("[0-9]+");
	}

	@Override
	public CommandResult execute(ShellEnvironment env, ArgumentValue[] args)
	{
		if (args.length == 6)
		{
			String doStatement = args[4].getValue();
			if (!doStatement.equalsIgnoreCase("do"))
			{
				env.getCommandSender().println("Unexpected code flow statement: '" + doStatement + "' at argument 4!");
				return CommandResult.ERROR;
			}

			if (!(args[0] instanceof VariableArgument))
			{
				env.getCommandSender().println("Argument 0 must be a variable!");
				return CommandResult.ERROR;
			}

			String a1 = args[1].getValue();
			String a2 = args[2].getValue();
			String a3 = args[3].getValue();

			if (!isInteger(a1))
			{
				env.getCommandSender().println("Not a number: '" + a1 + "'!");
				return CommandResult.ERROR;
			}

			if (!isInteger(a2))
			{
				env.getCommandSender().println("Not a number: '" + a2 + "'!");
				return CommandResult.ERROR;
			}

			if (!isInteger(a3))
			{
				env.getCommandSender().println("Not a number: '" + a3 + "'!");
				return CommandResult.ERROR;
			}

			Variable var = ((VariableArgument) args[0]).getVariable();

			int i1 = Integer.valueOf(a1);
			int i2 = Integer.valueOf(a2);
			int i3 = Integer.valueOf(a3);
			boolean goingUp = i3 >= 0;

			boolean called = false;
			String lastVal = "";
			for (int i = i1; goingUp ? i <= i2 : i >= i2; i += i3)
			{
				var.setValue(String.valueOf(i));
				called = true;

				lastVal = args[5].getValue();
			}

			return new CommandResult(lastVal, called, true);
		}

		if (args.length == 5)
		{
			String inStatement = args[1].getValue();
			if (!inStatement.equalsIgnoreCase("in"))
			{
				env.getCommandSender().println("Unexpected code flow statement: '" + inStatement + "' at argument 1!");
				return CommandResult.ERROR;
			}

			String doStatement = args[3].getValue();
			if (!doStatement.equalsIgnoreCase("do"))
			{
				env.getCommandSender().println("Unexpected code flow statement: '" + doStatement + "' at argument 3!");
				return CommandResult.ERROR;
			}

			if (!(args[0] instanceof VariableArgument))
			{
				env.getCommandSender().println("Argument 0 must be a variable!");
				return CommandResult.ERROR;
			}

			Variable var = ((VariableArgument) args[0]).getVariable();
			String[] lines = args[2].getValue().split("\\n");

			boolean called = false;
			String lastVal = "";
			for (String s : lines)
			{
				var.setValue(s);
				called = true;

				lastVal = args[4].getValue();
			}

			return new CommandResult(lastVal, called, true);
		}

		env.getCommandSender().println("Unknown number of arguments!");
		return CommandResult.ERROR;
	}

	@Override
	public String[] getAliases()
	{
		return ALIAS;
	}
}
