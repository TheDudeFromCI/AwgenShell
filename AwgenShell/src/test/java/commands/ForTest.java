package commands;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.mockito.InOrder;
import net.whg.awgenshell.exec.CommandSender;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.Permissions;

public class ForTest
{
	private void check(String cmd, String... out)
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		InOrder order = inOrder(sender);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand(cmd);

		for (String s : out)
			order.verify(sender).println(s);
	}

	@Test
	public void simpleCounter()
	{
		check("for $i, 1, 5, 1 do { print $i }", "1", "2", "3", "4", "5");
		check("$j = set 3; for $i, 1, $j do { print $i }", "1", "2", "3");
		check("for $i, 1, 3, 1 do { for $j, 1, 3, 1 do { print $i $j } }", "1 1", "1 2", "1 3", "2 1", "2 2", "2 3",
				"3 1", "3 2", "3 3");
	}
}
