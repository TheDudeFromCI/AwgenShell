package tokenization;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.junit.Test;
import org.mockito.InOrder;
import net.whg.awgenshell.exec.CommandSender;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.Permissions;

public class ComplexCommandsTest
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
	public void cmd1()
	{
		check("for $i, 1, 5, 1 do { $j = calc `ceil($i/2)`; $line = set ''; for $k, 1, $j, "
				+ "1, do { $line = append -s '' $line '#' }; print $line }", "#", "#", "##", "##", "###");
	}
}
