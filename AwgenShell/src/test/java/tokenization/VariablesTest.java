package tokenization;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.awgenshell.exec.CommandSender;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.Permissions;

public class VariablesTest
{
	@Test
	public void assignAndUseVariable()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("$var = set hello; print $var");
		verify(sender).println("hello");
	}

	@Test
	public void assignMultipleVariables()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("$var1 = set hello; $var2 = set world; print $var1 $var2");
		verify(sender).println("hello world");
	}

	@Test
	public void reassignVariable()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("$var = set red; $var = set blue; print $var");
		verify(sender).println("blue");
	}

	@Test
	public void numberVariable_FailToParse()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertFalse(shell.runCommand("$12 = set purple"));
	}
}
