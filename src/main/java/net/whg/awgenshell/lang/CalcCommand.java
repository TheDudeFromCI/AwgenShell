package net.whg.awgenshell.lang;

import net.whg.awgenshell.lang.equation.EquationSolver;
import net.whg.awgenshell.lang.equation.Val;
import net.whg.awgenshell.util.CommandResult;
import net.whg.awgenshell.util.template.BaseCommand;
import net.whg.awgenshell.util.template.CommandFlag;
import net.whg.awgenshell.util.template.CommandTemplateBuilder;

/**
 * Caclulates the value of a mathmatical formula given as an input. Supports
 * basic mathmatical functions: add, subtract, multiply, divide, raise to a
 * power, sqrt, sin, cos, tan, floor, ceil, and round.
 *
 * @author TheDudeFromCI
 */
public class CalcCommand extends BaseCommand
{
	private static EquationSolver solver = new EquationSolver();

	public CalcCommand()
	{
		super(new CommandTemplateBuilder().name("calc").alias("eval").perm("lang.calc")
				.subcommand("%- %*", (shell, args, flags) ->
				{
					try
					{
						boolean comma = false;

						for (CommandFlag flag : flags)
							if (flag.getName().equals("-f"))
								comma = true;

						Val v = solver.parse(args[0].getLast());
						return new CommandResult(v.format(comma), true, false);
					}
					catch (Exception exception)
					{
						shell.getCommandSender().println("Failed to parse equation! " + exception.getMessage());
						return CommandResult.ERROR;
					}
				}).flag("-f", 0).finishSubCommand().build());
	}
}
