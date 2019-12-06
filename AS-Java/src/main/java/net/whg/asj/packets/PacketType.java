package net.whg.asj.packets;

public enum PacketType
{
    USER_CONNECTED("USER_CONNECTED"),
    USER_DISCONNECTED("USER_DISCONNECTED"),
    REGISTER_COMMAND("REGISTER_COMMAND"),
    UNREGISTER_COMMAND("UNREGISTER_COMMMAND"),
    SEND_COMMAND("SEND_COMMAND"),
    TERMINAL_INPUT("TERMINAL_INPUT"),
    TERMINAL_OUTPUT("TERMINAL_OUTPUT"),
    RUN_COMMAND("RUN_COMMAND"),
    COMMAND_EXIT("COMMAND_EXIT");

    private final String name;

    private PacketType(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public static PacketType getFromName(String s)
    {
        s = s.toUpperCase();

        for (PacketType t : values())
            if (t.getName()
                 .equals(s))
                return t;

        throw new UnknownPacketException(s);
    }
}
