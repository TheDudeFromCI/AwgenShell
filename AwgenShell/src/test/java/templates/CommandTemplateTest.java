package templates;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.awgenshell.arg.CommandArgument;
import net.whg.awgenshell.arg.StringArgument;
import net.whg.awgenshell.arg.Variable;
import net.whg.awgenshell.arg.VariableArgument;
import net.whg.awgenshell.util.template.CommandTemplate;
import net.whg.awgenshell.util.template.CommandTemplateBuilder;
import net.whg.awgenshell.util.template.InputArgument;
import net.whg.awgenshell.util.template.SubCommand;
import net.whg.awgenshell.util.template.SubCommandExecutor;

public class CommandTemplateTest
{
	private SubCommand getSubCommand(CommandTemplate template, String... values)
	{
		InputArgument[] args = new InputArgument[values.length];

		for (int i = 0; i < args.length; i++)
		{
			if (values[i].equals("$"))
				args[i] = new InputArgument(new VariableArgument(new Variable("var", values[i].substring(1))));
			if (values[i].equals("{}"))
			{
				CommandArgument cmd = mock(CommandArgument.class);
				when(cmd.getValue()).thenThrow(new RuntimeException());

				args[i] = new InputArgument(cmd);

			}
			else
				args[i] = new InputArgument(new StringArgument(values[i]));
		}

		return template.getSubcommand(args);
	}

	@Test
	public void matchesSingleWord()
	{
		SubCommandExecutor exe = mock(SubCommandExecutor.class);
		CommandTemplate template = new CommandTemplateBuilder().subcommand("hello %n", exe).build();

		assertNotNull(getSubCommand(template, "hello"));

		assertNull(getSubCommand(template, "hello", "there"));
		assertNull(getSubCommand(template, "$"));
		assertNull(getSubCommand(template));
		assertNull(getSubCommand(template, "hi"));
	}

	@Test
	public void matchesNumber()
	{
		SubCommandExecutor exe = mock(SubCommandExecutor.class);
		CommandTemplate template = new CommandTemplateBuilder().subcommand("%# %n", exe).build();

		assertNotNull(getSubCommand(template, "-1"));
		assertNotNull(getSubCommand(template, "3"));
		assertNotNull(getSubCommand(template, "100000"));

		assertNull(getSubCommand(template, "2.0"));
		assertNull(getSubCommand(template, "abc"));
		assertNull(getSubCommand(template, "1..2"));
		assertNull(getSubCommand(template, "*"));
	}

	@Test
	public void matchesAnyNumberOfArgs()
	{
		SubCommandExecutor exe = mock(SubCommandExecutor.class);
		CommandTemplate template = new CommandTemplateBuilder().subcommand("%**", exe).build();

		assertNotNull(getSubCommand(template));
		assertNotNull(getSubCommand(template, "1"));
		assertNotNull(getSubCommand(template, "1", "hello"));
		assertNotNull(getSubCommand(template, "1.654", "", "3"));
		assertNotNull(getSubCommand(template, "$", "2", "$", "{}", "{}"));
	}
}
