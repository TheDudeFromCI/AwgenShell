package net.whg.awgenshell.util.template;

/**
 * This exception is called when a user provides flags for a command which are
 * not allowed.
 * 
 * @author TheDudeFromCI
 */
public class InvalidFlagsException extends RuntimeException
{
	private static final long serialVersionUID = 2586945873032868898L;

	public InvalidFlagsException(String message)
	{
		super(message);
	}
}
