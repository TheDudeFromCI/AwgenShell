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

public class InlineCommandsTest
{
	@Test
	public void print_setInline()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("print Hello (set Steve) !");
		verify(sender).println("Hello Steve!");
	}

	@Test
	public void multiple_nested_commands()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("print Hello (set (set (set Steve))) !");
		verify(sender).println("Hello Steve!");
	}

	@Test
	public void unclosed_inline()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertFalse(shell.runCommand("print Hello (set World!"));
	}

	@Test
	public void unopened_inline()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertFalse(shell.runCommand("print Hello set World!)"));
	}

	@Test
	public void unopened_nested_inline()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertFalse(shell.runCommand("print Hello (set set World!))"));
	}

	@Test
	public void nested_indirect()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertTrue(shell.runCommand("print Hello {set Steve} !"));
		verify(sender).println("Hello Steve!");
	}

	@Test
	public void nested_indirect_chain()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertTrue(shell.runCommand("print My favorite color is {set red; set blue}"));
		verify(sender).println("My favorite color is blue");
	}
}
