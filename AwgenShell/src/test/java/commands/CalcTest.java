package commands;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.awgenshell.exec.CommandSender;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.perms.Permissions;

public class CalcTest
{
	private void checkFormula(String formula, String answer)
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("calc '" + formula + "'");
		verify(sender).println(answer);
	}

	@Test
	public void formulas()
	{
		checkFormula("1+2", "3");
		checkFormula("7*7", "49");
		checkFormula("10/5", "2");
		checkFormula("2*(1+1)", "4");
		checkFormula("( 2 * 7 + 3 - 1 ) % 4^2", "0");
		checkFormula("10000 / 100", "100");
		checkFormula("10001 / 2", "5,000.5");
		checkFormula("2^256",
				"115,792,089,237,316,195,423,570,985,008,687,907,853,269,984,665,640,564,039,457,584,007,913,129,639,936");
		checkFormula("sqrt(25)", "5");
		checkFormula("sqrt(16), sqrt(100) + 3, floor(2.5)", "[4, 13, 2]");
		checkFormula("sqrt(ceil(15.8))", "4");
		checkFormula("sqrt(81, 49)", "[9, 7]");
	}

	@Test
	public void functions()
	{
		checkFormula("sqrt(100, 10000, 16)", "[10, 100, 4]");
		checkFormula("floor(2.5)", "2");
	}

	@Test
	public void complexNumbers()
	{
		checkFormula("c(1, 0) + c(3, 3)", "4 + 3i");
	}

	@Test
	public void vectors()
	{

	}
}
