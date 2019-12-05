package net.whg.asj.auth;

import java.util.HashMap;
import java.util.Map;
import net.whg.asj.packets.IPacket;

public class LoginPacket implements IPacket
{
    private static final String[] PROPERTIES =
    {
            "username", "password", "token"
    };

    private final Map<String, String> data = new HashMap<>();

    public LoginPacket()
    {
        for (String s : PROPERTIES)
            data.put(s, "");
    }

    @Override
    public String getName()
    {
        return "LOGIN";
    }

    @Override
    public String[] getProperties()
    {
        return PROPERTIES;
    }

    @Override
    public String getData(String property)
    {
        return data.get(property);
    }

    @Override
    public void setData(String property, String value)
    {
        data.put(property, value);
    }

    @Override
    public void handle()
    {
        // TODO
    }

    public void setUsername(String username)
    {
        setData("username", username);
    }

    public void setPassword(String password)
    {
        setData("password", password);
    }

    public void setToken(String token)
    {
        setData("token", token);
    }

    public String getUsername()
    {
        return getData("username");
    }

    public String getToken()
    {
        return getData("token");
    }
}
