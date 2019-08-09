package net.whg.awgenshell.lang;

import net.whg.awgenshell.util.CommandResult;
import net.whg.awgenshell.util.ShellUtils;
import net.whg.awgenshell.util.template.BaseCommand;
import net.whg.awgenshell.util.template.CommandTemplateBuilder;

/**
 * Preforms a command continuously while the given condition returns true.
 *
 * @author TheDudeFromCI
 */
public class WhileCommand extends BaseCommand
{
	public WhileCommand()
	{
		super(new CommandTemplateBuilder().name("while").perm("lang.while")
				.subcommand("%* do %{}", (shell, args, flags) ->
				{
					boolean called = false;
					String lastVal = "";
					while (ShellUtils.stringToBoolean(args[0].run()))
					{
						lastVal = args[2].run();
						called = true;
					}

					return new CommandResult(lastVal, called, true);
				}).finishSubCommand().build());
	}
}
