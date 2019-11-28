package net.whg.asj;

import net.whg.asj.User;

public class ShellEnvironment
{
    private final User user;

    public ShellEnvironment(User user)
    {
        this.user = user;
    }

    public void sendCommand(String command)
    {
        // TODO
    }

    public User getUser()
    {
        return user;
    }
}
