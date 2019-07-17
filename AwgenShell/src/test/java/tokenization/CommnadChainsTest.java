package tokenization;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import net.whg.awgenshell.CommandSender;
import net.whg.awgenshell.Permissions;
import net.whg.awgenshell.ShellEnvironment;

public class CommnadChainsTest
{
	@Test
	public void singleCommand_helloworld_pass()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("print hello world");
		verify(sender).println("hello world");
	}

	@Test
	public void twoCommands_simpleChain_pass()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);
		InOrder inOrder = Mockito.inOrder(sender);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("print hello world; print Sup?");
		inOrder.verify(sender).println("hello world");
		inOrder.verify(sender).println("Sup?");
	}

	@Test
	public void twoCommands_and_firstCommandPasses()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("random 10 & print Sup?");
		verify(sender).println("Sup?");
	}

	@Test
	public void twoCommands_and_firstCommandFails()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("random apple & print Sup?");
		verify(sender, never()).println("Sup?");
	}

	@Test
	public void threeCommands_and_firstCommandFails()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("random apple & print Sup? & print Hey");
		verify(sender, never()).println("Sup?");
		verify(sender, never()).println("Hey");
	}

	@Test
	public void twoCommands_or_firstCommandPasses()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("random 10 | print Sup?");
		verify(sender, never()).println("Sup?");
	}

	@Test
	public void threeCommands_andOr_firstCommandFails()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("random apple & print Sup? | print Hey");
		verify(sender, never()).println("Sup?");
		verify(sender).println("Hey");
	}

	@Test
	public void unknownCharacter_percent_fail()
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		assertFalse(shell.runCommand("print hi % print Hey"));
		verify(sender, never()).println("Hey");
		verify(sender, never()).println("Sup?");
	}
}
