package commands;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import org.junit.Test;
import net.whg.awgenshell.lang.equation.EquationSolver;
import net.whg.awgenshell.lang.equation.IEquationFunction;

public class EquationSolverTest
{
	@Test
	public void addNullFunction()
	{
		EquationSolver solver = new EquationSolver();

		int count = solver.getFunctionCount();
		solver.addFunction(null);

		assertEquals(count, solver.getFunctionCount());
	}

	@Test
	public void addFunctionTwice()
	{
		IEquationFunction function = mock(IEquationFunction.class);
		EquationSolver solver = new EquationSolver();

		int count = solver.getFunctionCount();
		solver.addFunction(function);
		solver.addFunction(function);

		assertEquals(count + 1, solver.getFunctionCount());
	}
}
