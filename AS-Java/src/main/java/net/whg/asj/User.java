package net.whg.asj;

public class User
{
    private final String username;
    private final String token;

    public User(String username, String token)
    {
        this.username = username;
        this.token = token;
    }

    public String getUsername()
    {
        return username;
    }

    public String getToken()
    {
        return token;
    }
}
