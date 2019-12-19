package util;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.InOrder;
import net.whg.awgenshell.exec.CommandSender;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.Permissions;

public class Checks
{
	public static void check(String cmd, String... out)
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		InOrder order = inOrder(sender);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand(cmd);
		for (String s : out)
			order.verify(sender).println(s);

		verify(sender, times(out.length)).println(any());
	}
}
