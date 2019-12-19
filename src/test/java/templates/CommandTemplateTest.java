package templates;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
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
		List<InputArgument> args = new ArrayList<>();

		for (String value : values)
		{
			if (value.equals("$"))
				args.add(new InputArgument(new VariableArgument(new Variable("var", value.substring(1)))));
			else if (value.equals("{}"))
			{
				CommandArgument cmd = mock(CommandArgument.class);
				when(cmd.getValue()).thenThrow(new RuntimeException());

				args.add(new InputArgument(cmd));
			}
			else
				args.add(new InputArgument(new StringArgument(value)));
		}

		return template.getSubcommand(args);
	}

	@Test
	public void matchesSingleWord()
	{
		SubCommandExecutor exe = mock(SubCommandExecutor.class);
		CommandTemplate template = new CommandTemplateBuilder().subcommand("hello %n", exe).finishSubCommand().build();

		assertNotNull(getSubCommand(template, "hello"));

		assertNull(getSubCommand(template, "hello", "there"));
		assertNull(getSubCommand(template, "$a"));
		assertNull(getSubCommand(template));
		assertNull(getSubCommand(template, "hi"));
	}

	@Test
	public void matchesNumber()
	{
		SubCommandExecutor exe = mock(SubCommandExecutor.class);
		CommandTemplate template = new CommandTemplateBuilder().subcommand("%# %n", exe).finishSubCommand().build();

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
		CommandTemplate template = new CommandTemplateBuilder().subcommand("%**", exe).finishSubCommand().build();

		assertNotNull(getSubCommand(template));
		assertNotNull(getSubCommand(template, "1"));
		assertNotNull(getSubCommand(template, "1", "hello"));
		assertNotNull(getSubCommand(template, "-1.654", "", "3"));
		assertNotNull(getSubCommand(template, "$red", "2", "$blue", "{}", "{}"));
	}
}
