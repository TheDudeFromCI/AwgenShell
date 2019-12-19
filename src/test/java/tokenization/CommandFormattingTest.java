package tokenization;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.awgenshell.exec.CommandSender;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.Permissions;

public class CommandFormattingTest
{
	@Test
	public void newlines_between_commands()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertTrue(shell.runCommand("print hello;\nprint world."));
	}

	@Test
	public void newlines_between_arguments()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertTrue(shell.runCommand("print hello\nworld."));
	}

	@Test
	public void newlines_within_quotes()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertTrue(shell.runCommand("print \"hello\nworld\""));
		verify(sender).println("hello\nworld");
	}

	@Test
	public void newlines_within_unmatchedQuotes()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertFalse(shell.runCommand("print \"hello\nworld'"));
	}
}
