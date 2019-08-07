package net.whg.awgenshell.lang.equation;

/**
 * This interface represents an equation function which takes an input and
 * calculates an output.
 *
 * @author TheDudeFromCI
 */
public interface IEquationFunction
{
	/**
	 * Gets the name of the function. This value should be in all lower case and
	 * should only include letters, or underscores
	 *
	 * @return The name of this function.
	 */
	String getName();

	/**
	 * Takes in an equation value and solves for that equation.
	 *
	 * @param x
	 *     - The input value.
	 * @return The output value.
	 * @throws EquationParserException
	 *     - If an incorrect input is given.
	 */
	Val solve(Val x);
}
