package templates;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import net.whg.awgenshell.arg.ArgumentValue;
import net.whg.awgenshell.arg.CommandArgument;
import net.whg.awgenshell.arg.StringArgument;
import net.whg.awgenshell.arg.Variable;
import net.whg.awgenshell.arg.VariableArgument;
import net.whg.awgenshell.util.template.CommandTemplate;
import net.whg.awgenshell.util.template.CommandTemplateBuilder;
import net.whg.awgenshell.util.template.SubCommand;
import net.whg.awgenshell.util.template.SubCommandExecutor;

public class CommandTemplateTest
{
	private SubCommand getSubCommand(CommandTemplate template, String... values)
	{
		ArgumentValue[] args = new ArgumentValue[values.length];

		for (int i = 0; i < args.length; i++)
		{
			if (values[i].equals("$"))
				args[i] = new VariableArgument(new Variable(""));
			if (values[i].equals("{}"))
				args[i] = new CommandArgument(null, false);
			else
				args[i] = new StringArgument(values[i]);
		}

		return template.getSubcommand(args, values);
	}

	@Test
	public void matchesSingleWord()
	{
		SubCommandExecutor exe = mock(SubCommandExecutor.class);
		CommandTemplate template = new CommandTemplateBuilder().subCommand("hello %n", exe).build();

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
		CommandTemplate template = new CommandTemplateBuilder().subCommand("%# %n", exe).build();

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
		CommandTemplate template = new CommandTemplateBuilder().subCommand("%**", exe).build();

		assertNotNull(getSubCommand(template));
		assertNotNull(getSubCommand(template, "1"));
		assertNotNull(getSubCommand(template, "1", "2"));
		assertNotNull(getSubCommand(template, "1", "2", "3"));
		assertNotNull(getSubCommand(template, "1", "2", "3", "4"));
	}
}
