package net.whg.awgenshell.arg;

/**
 * A single argument that is provided to the command when it is executed.
 *
 * @author TheDudeFromCI
 */
public interface ArgumentValue
{
	/**
	 * Gets the current value of this argument as a string. This value is not
	 * promised to be consistant and may be recalculated each time this method is
	 * called. If this argument is a dynamic command, then that command is executed
	 * each time that the this method is called. In most cases, it is ideal to call
	 * this method only once and store the value in a local variable unless calling
	 * the method multiple times is intended.
	 *
	 * @return The current value of this argument.
	 */
	String getValue();
}
