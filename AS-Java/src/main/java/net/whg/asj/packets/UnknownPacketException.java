package net.whg.asj.packets;

public class UnknownPacketException extends RuntimeException
{
    private static final long serialVersionUID = 3245908272349L;

    public UnknownPacketException(String type)
    {
        super("Unknown packet type: " + type);
    }
}
