package net.whg.awgenshell.lang.equation;

/**
 * An exception which is thrown while attempting to parse a string equation.
 *
 * @author TheDudeFromCI
 */
public class EquationParserException extends RuntimeException
{
	private static final long serialVersionUID = 8761851481948354616L;

	/**
	 * Creates a new EquationParserException with no message.
	 */
	public EquationParserException()
	{
		super();
	}

	/**
	 * Creates a new EquationParserException with the given message.
	 *
	 * @param message
	 *     - The error message.
	 */
	public EquationParserException(String message)
	{
		super(message);
	}
}
