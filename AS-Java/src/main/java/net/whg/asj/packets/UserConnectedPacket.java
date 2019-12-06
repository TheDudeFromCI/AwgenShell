package net.whg.asj.packets;

public class UserConnectedPacket implements IPacket
{
    private static final String[] PROPERTIES = {"username", "password", "namespace"};

    @Override
    public PacketType getType()
    {
        return PacketType.USER_CONNECTED;
    }

    @Override
    public String[] getProperties()
    {
        return null;
    }

    @Override
    public String getData(String property)
    {
        return null;
    }

    @Override
    public void setData(String property, String value)
    {}

    @Override
    public void handle()
    {}
}
