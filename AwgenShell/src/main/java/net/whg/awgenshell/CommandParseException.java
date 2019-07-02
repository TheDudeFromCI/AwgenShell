package net.whg.awgenshell;

/**
 * Thrown if there is an issue with parsing the input string.
 *
 * @author TheDudeFromCI
 */
public class CommandParseException extends RuntimeException
{
	private static final long serialVersionUID = 6932263312565399856L;

	/**
	 * Creates a new command parse exception.
	 *
	 * @param string
	 *     - The error message.
	 */
	public CommandParseException(String string, Token token)
	{
		super(string + "; Token '" + token.getValue() + "' at index " + token.getPos());
	}
}
