package net.whg.asj.auth;

import net.whg.asj.ShellEnvironment;
import net.whg.asj.User;
import net.whg.asj.auth.AuthenticationFailureException;

/**
 * This class represents a new shell environment being created, awaiting authentication. This class will return a new
 * shell environment when authentication is successful, or return a failure state if the user cannot be authenticated.
 */
public class EnvironmentFuture
{
    private final User user;
    private int status;

    public EnvironmentFuture(User user, PendingEnvironments pendingEnvironments)
    {
        this.user = user;
        pendingEnvironments.addFuture(this);
    }

    public ShellEnvironment sync()
    {
        while (true)
        {
            synchronized (this)
            {
                if (status != 0)
                    break;
            }

            try
            {
                Thread.sleep(1);
            }
            catch (Exception e)
            {
            }
        }

        synchronized (this)
        {
            if (status > 0)
                return new ShellEnvironment(user);

            throw new AuthenticationFailureException("Invalid username or password for " + user.getUsername());
        }
    }

    void markFailure()
    {
        synchronized (this)
        {
            status = -1;
        }
    }

    void markSuccess()
    {
        synchronized (this)
        {
            status = 1;
        }
    }
}
