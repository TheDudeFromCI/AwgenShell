package net.whg.asj.env;

import net.whg.asj.conn.IConnection;
import net.whg.asj.conn.LoginCredentials;

/**
 * This class represents a new shell environment being created, awaiting authentication. This class will return a new
 * shell environment when authentication is successful, or return a failure state if the user cannot be authenticated.
 */
public class EnvironmentFuture
{
    public EnvironmentFuture(IConnection connection, LoginCredentials login)
    {
        Thread t = new Thread(() ->
        {
            connection.
        });

        t.setDaemon(true);
        t.start();
    }
}
