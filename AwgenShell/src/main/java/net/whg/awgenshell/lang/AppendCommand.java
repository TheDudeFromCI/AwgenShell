package net.whg.awgenshell.lang;

import net.whg.awgenshell.util.CommandResult;
import net.whg.awgenshell.util.template.BaseCommand;
import net.whg.awgenshell.util.template.CommandFlag;
import net.whg.awgenshell.util.template.CommandTemplateBuilder;

/**
 * Adds two or more strings together, using an optional seperator string. Can
 * use the "-s [seperator]" flag to assign the string seperator. the "-n" flag
 * can be used to set newline characters as the string seperator.
 *
 * @author TheDudeFromCI
 */
public class AppendCommand extends BaseCommand
{
	public AppendCommand()
	{
		super(new CommandTemplateBuilder().name("append").alias("add").alias("join").alias("concat").perm("lang.append")
				.subcommand("%- %**", (shell, args, flags) ->
				{
					String seperator = "";
					for (CommandFlag f : flags)
					{
						if (f.getName().equals("-n"))
							seperator = "\n";
						else if (f.getName().equals("-s"))
							seperator = f.getValues()[0];
					}

					String line = "";
					for (int i = 0; i < args.length; i++)
					{
						if (i > 0)
							line += seperator;

						line += args[i].getLast();
					}

					return new CommandResult(line, true, false);
				}).flag("-n", 0).flag("-s", 1).finishSubCommand().build());
	}
}
