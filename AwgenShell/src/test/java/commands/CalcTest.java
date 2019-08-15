package commands;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Test;
import net.whg.awgenshell.exec.CommandSender;
import net.whg.awgenshell.exec.ShellEnvironment;
import net.whg.awgenshell.lang.equation.EquationParserException;
import net.whg.awgenshell.lang.equation.EquationSolver;
import net.whg.awgenshell.perms.Permissions;

public class CalcTest
{
	private void checkFormula(String formula, String answer)
	{
		checkFormula(formula, "", answer);
	}

	private void checkFormula(String formula, String flags, String answer)
	{
		CommandSender sender = mock(CommandSender.class);
		when(sender.getPermissions()).thenReturn(Permissions.ALL);

		ShellEnvironment shell = new ShellEnvironment(sender);

		shell.runCommand("calc " + flags + " '" + formula + "'");
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
		checkFormula("10001 / 2", "-f", "5,000.5");
		checkFormula("2^256", "-f",
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
		checkFormula("floor(2.5, 1, -1.9)", "[2, 1, -2]");
		checkFormula("round(9.8, 2.1, -3.49)", "[10, 2, -3]");
		checkFormula("ceil(5.5, 4.1, 7, -1, -1.1)", "[6, 5, 7, -1, -1]");
		checkFormula("floor(sin(1) * 1000000) / 1000000", "0.84147");
		checkFormula("floor(cos(1) * 1000000) / 1000000", "0.540302");
		checkFormula("floor(tan(1) * 1000000) / 1000000", "1.557407");
	}

	@Test
	public void complexNumbers()
	{
		checkFormula("c(1, 0) + c(3, 3)", "4 + 3i");
		checkFormula("c(0, 1) * c(0, 1)", "-1");
		checkFormula("1 + c(0, 1)", "1 + i");
		checkFormula("3 + 5i", "3 + 5i");
		checkFormula("3 + 5i + 3 * 5 + 4i", "18 + 9i");
		checkFormula("3 + i", "3 + i");
		checkFormula("(3 + 4i) * 27 + 12i * (1 + i)", "69 + 120i");
	}

	@Test
	public void vectors()
	{
		checkFormula("v(0, 3, 6) + v(1, -4, -2)", "(1, -1, 4)");
		checkFormula("v(1, 2, 3) * 3", "(3, 6, 9)");
		checkFormula("v(1, 2, 3) * (1 + 3i)", "(1 + 3i, 2 + 6i, 3 + 9i)");
	}

	@Test(expected = EquationParserException.class)
	public void doubleDecimals()
	{
		new EquationSolver().parse("1.2.3");
	}
}
