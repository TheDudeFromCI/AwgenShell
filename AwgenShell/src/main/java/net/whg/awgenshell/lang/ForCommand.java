package net.whg.awgenshell.lang;

import net.whg.awgenshell.ArgumentValue;
import net.whg.awgenshell.CommandHandler;
import net.whg.awgenshell.CommandResult;
import net.whg.awgenshell.CommandSender;
import net.whg.awgenshell.Variable;
import net.whg.awgenshell.VariableArgument;

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
	public CommandResult execute(CommandSender sender, ArgumentValue[] args)
	{
		if (args.length == 6)
		{
			String doStatement = args[4].getValue();
			if (!doStatement.equalsIgnoreCase("do"))
			{
				sender.println("Unexpected code flow statement: '" + doStatement + "' at argument 4!");
				return CommandResult.ERROR;
			}

			if (!(args[0] instanceof VariableArgument))
			{
				sender.println("Argument 0 must be a variable!");
				return CommandResult.ERROR;
			}

			String a1 = args[1].getValue();
			String a2 = args[2].getValue();
			String a3 = args[3].getValue();

			if (!isInteger(a1))
			{
				sender.println("Not a number: '" + a1 + "'!");
				return CommandResult.ERROR;
			}

			if (!isInteger(a2))
			{
				sender.println("Not a number: '" + a2 + "'!");
				return CommandResult.ERROR;
			}

			if (!isInteger(a3))
			{
				sender.println("Not a number: '" + a3 + "'!");
				return CommandResult.ERROR;
			}

			Variable var = ((VariableArgument) args[0]).getVariable();

			int i1 = Integer.valueOf(a1);
			int i2 = Integer.valueOf(a2);
			int i3 = Integer.valueOf(a3);
			boolean goingUp = i3 >= 0;

			boolean called = false;
			String lastVal = "";
			for (int i = i1; goingUp ? i1 <= i2 : i1 >= i2; i += i3)
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
				sender.println("Unexpected code flow statement: '" + inStatement + "' at argument 1!");
				return CommandResult.ERROR;
			}

			String doStatement = args[3].getValue();
			if (!doStatement.equalsIgnoreCase("do"))
			{
				sender.println("Unexpected code flow statement: '" + doStatement + "' at argument 3!");
				return CommandResult.ERROR;
			}

			if (!(args[0] instanceof VariableArgument))
			{
				sender.println("Argument 0 must be a variable!");
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

		sender.println("Unknown number of arguments!");
		return CommandResult.ERROR;
	}

	@Override
	public String[] getAliases()
	{
		return ALIAS;
	}
}
