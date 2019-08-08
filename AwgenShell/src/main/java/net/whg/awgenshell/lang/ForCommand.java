package net.whg.awgenshell.lang;

import net.whg.awgenshell.arg.Variable;
import net.whg.awgenshell.arg.VariableArgument;
import net.whg.awgenshell.util.CommandResult;
import net.whg.awgenshell.util.template.BaseCommand;
import net.whg.awgenshell.util.template.CommandTemplateBuilder;

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
public class ForCommand extends BaseCommand
{
	public ForCommand()
	{
		super(new CommandTemplateBuilder().name("for").alias("foreach").perm("lang.for")
				.subcommand("%$ %# %# %# do %{}", (shell, inputs) ->
				{
					Variable var = ((VariableArgument) inputs[0].getArgument()).getVariable();

					int i1 = Integer.valueOf(inputs[1].getLast());
					int i2 = Integer.valueOf(inputs[2].getLast());
					int i3 = Integer.valueOf(inputs[3].getLast());
					boolean goingUp = i3 >= 0;

					boolean called = false;
					String lastVal = "";
					for (int i = i1; goingUp ? i <= i2 : i >= i2; i += i3)
					{
						var.setValue(String.valueOf(i));
						called = true;

						lastVal = inputs[5].run();
					}

					return new CommandResult(lastVal, called, true);
				}).subcommand("%$ %# %# do %{}", (shell, inputs) ->
				{
					Variable var = ((VariableArgument) inputs[0].getArgument()).getVariable();

					int i1 = Integer.valueOf(inputs[1].getLast());
					int i2 = Integer.valueOf(inputs[2].getLast());

					boolean called = false;
					String lastVal = "";
					for (int i = i1; i <= i2; i++)
					{
						var.setValue(String.valueOf(i));
						called = true;

						lastVal = inputs[4].run();
					}

					return new CommandResult(lastVal, called, true);
				}).subcommand("%$ in %* do %{}", (shell, inputs) ->
				{
					Variable var = ((VariableArgument) inputs[0].getArgument()).getVariable();
					String[] lines = inputs[2].getLast().split("\\n");

					boolean called = false;
					String lastVal = "";
					for (String s : lines)
					{
						var.setValue(s);
						called = true;

						lastVal = inputs[4].run();
					}

					return new CommandResult(lastVal, called, true);
				}).build());
	}
}
