package net.whg.awgenshell;

/**
 * And instance of the the command wrapper for who executed the command.
 *
 * @author TheDudeFromCI
 */
public interface CommandSender
{
	/**
	 * The name of this command sender.
	 *
	 * @return The name of this command sender.
	 */
	String getName();

	/**
	 * Sends a message to this command sender.
	 *
	 * @param message
	 *     - The message to send.
	 */
	void println(String message);

	/**
	 * Gets the permissions object attached to this command sender. By default, this
	 * implementation assumes the user has all lang permissions and no others.
	 *
	 * @return The permissions for this command sender.
	 */
	default Permissions getPermissions()
	{
		return Permissions.LANG;
	}
}
