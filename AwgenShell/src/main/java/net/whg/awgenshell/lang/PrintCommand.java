package net.whg.awgenshell.lang;

import net.whg.awgenshell.util.CommandResult;
import net.whg.awgenshell.util.template.BaseCommand;
import net.whg.awgenshell.util.template.CommandTemplateBuilder;

/**
 * The print command adds all inputs together into a string and sends that
 * message to the user. If multiple arguments are provided, they are added
 * together. If an argument starts with a letter or number, then a space is
 * automatically added between the arguments.
 *
 * @author TheDudeFromCI
 */
public class PrintCommand extends BaseCommand
{
	public PrintCommand()
	{
		super(new CommandTemplateBuilder().name("print").alias("echo").alias("say").perm("lang.print")
				.subcommand("%**", (shell, args, flags) ->
				{
					String line = "";
					for (int i = 0; i < args.length; i++)
					{
						if (i > 0 && !line.endsWith(" ") && args[i].getLast().matches("[a-zA-z0-9].*"))
							line += " ";

						line += args[i].getLast();
					}

					shell.getCommandSender().println(line);
					return CommandResult.SUCCESS;
				}).finishSubCommand().build());
	}
}
