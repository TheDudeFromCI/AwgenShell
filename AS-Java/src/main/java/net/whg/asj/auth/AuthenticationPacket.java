package net.whg.asj.auth;

import java.util.HashMap;
import java.util.Map;
import net.whg.asj.AwgenShell;
import net.whg.asj.packets.IPacket;

public class AuthenticationPacket implements IPacket
{
    private static final String[] PROPERTIES =
    {
            "token", "status"
    };

    private final AwgenShell shell;
    private final Map<String, String> data = new HashMap<>();

    public AuthenticationPacket(AwgenShell shell)
    {
        this.shell = shell;

        data.put("token", "");
        data.put("status", "false");
    }

    @Override
    public String getName()
    {
        return "AUTHENTICATE";
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
}
