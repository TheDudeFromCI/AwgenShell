package net.whg.asj;

import net.whg.asj.conn.LoginCredentials;
import net.whg.asj.env.EnvironmentFuture;

public final class AwgenShell
{
    private final IConnection connection;

    public AwgenShell(IConnection connection)
    {
        this.connection = connection;
    }

    public EnvironmentFuture createEnvironment(LoginCredentials login)
    {
        return new EnvironmentFuture(connection, login);
    }
}
