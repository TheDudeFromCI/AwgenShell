package net.whg.asj.conn;

import java.io.OutputStream;
import net.whg.asj.conn.IConnectionResult;

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
     * Gets the output stream to write to the server.
     * 
     * @return The output stream, or null if a connection is not open.
     */
    OutputStream getOutputStream();

    /**
     * Gets the input stream to read from the server.
     * 
     * @return The input stream, or null if a connection is not open.
     */
    InputStream getInputStream();
}
