package net.whg.asj.conn;

import java.net.SocketException;
import net.whg.asj.packets.IPacket;

/**
 * This interface represents a connection to the AwgenShell server. This class handles maintaining the connection and
 * communicating with the server.
 */
public interface IConnection
{
    /**
     * Attempts to connect to the server. Does nothing if a connection is already established. If a connection could not
     * be made, this method fails silently.
     * 
     * @return True is a connection was successfully made. False otherwise. If a connection was already established,
     *         true is returned.
     */
    boolean attemptConnect();

    /**
     * Checks if a connection to the server is currently active or not.
     * 
     * @return True if a connection is active. False otherwise.
     */
    boolean isConnected();

    /**
     * Sends a packet to the server. This method may be called from any thread.
     * 
     * @param packet
     *                   - The packet to send.
     * @throws IllegalStateException
     *                                   If the connection is not open.
     */
    void sendPacket(IPacket packet);

    /**
     * Retrieves the next packet from the server. This method blocks until a packet is recieved.
     * 
     * @return The next packet sent by the server.
     * @throws IllegalStateException
     *                                   If the connection is not open.
     * @throws SocketException
     *                                   If the connection is closed while waiting.
     */
    IPacket recievePacket() throws SocketException;
}
