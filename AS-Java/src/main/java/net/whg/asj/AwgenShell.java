package net.whg.asj;

import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import net.whg.asj.conn.IConnection;
import net.whg.asj.packets.IPacket;

public final class AwgenShell
{
    private final List<IPacket> packets = new LinkedList<>();
    private final IConnection connection;

    public AwgenShell(IConnection connection)
    {
        if (!connection.attemptConnect())
            throw new IllegalStateException("Failed to connect to server!");

        this.connection = connection;

        Thread connectionThread = new Thread(() ->
        {
            try
            {
                while (true)
                {
                    IPacket packet = connection.recievePacket();

                    synchronized (packets)
                    {
                        packets.add(packet);
                    }
                }
            }
            catch (SocketException e)
            {
                // Connection closed
            }
        });

        connectionThread.setDaemon(true);
        connectionThread.start();
    }

    public void handlePackets()
    {
        handlePackets(Integer.MAX_VALUE);
    }

    public void handlePackets(int maxPackets)
    {
        IPacket packet;
        for (int i = 0; i < maxPackets; i++)
        {
            synchronized (packets)
            {
                if (packets.isEmpty())
                    return;

                packet = packets.remove(0);
            }

            packet.handle();
        }
    }

    public ShellEnvironment createEnvironment(String username)
    {
        User user = new User(username);
        return new ShellEnvironment(user);
    }

    void sendPacket(IPacket packet)
    {
        connection.sendPacket(packet);
    }
}
