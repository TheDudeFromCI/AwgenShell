package commands;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.awgenshell.exec.CommandSender;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.Permissions;

public class WhileTest
{
	@Test(timeout = 1000)
	public void basicLoop()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("$e = set 3; while $e do { print $e; $e = calc `$e - 1` }");

		verify(sender).println("3");
		verify(sender).println("2");
		verify(sender).println("1");
		verify(sender, never()).println("0");
	}

	@Test(timeout = 1000)
	public void skipLoop()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("while false do { print red }");
		verify(sender, never()).println("red");
	}
}
