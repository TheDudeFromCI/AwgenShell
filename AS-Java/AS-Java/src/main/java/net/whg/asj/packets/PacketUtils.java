package net.whg.asj.packets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import net.whg.asj.auth.LoginPacket;

public final class PacketUtils
{
    private PacketUtils()
    {
    }

    public static IPacket createPacket(String type)
    {
        switch (type)
        {
            case "LOGIN":
                return new LoginPacket();

            default:
                throw new UnknownPacketException(type);
        }
    }

    public static void writePacket(OutputStream out, IPacket packet) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        sb.append(packet.getName()).append('\n');

        for (String s : packet.getProperties())
        {
            String value = packet.getData(s);
            value = URLEncoder.encode(value, StandardCharsets.UTF_8);

            sb.append(s).append('=').append(value).append('\n');
        }

        byte[] bytes = sb.toString().getBytes(StandardCharsets.UTF_8);
        int byteCount = bytes.length;

        out.write((byteCount << 24) & 0xFF);
        out.write((byteCount << 16) & 0xFF);
        out.write((byteCount << 8) & 0xFF);
        out.write(byteCount & 0xFF);
        out.write(bytes);
    }

    public static IPacket readPacket(InputStream in) throws IOException
    {
        int byteCount = 0;
        byteCount |= (in.read() & 0xFF) << 24;
        byteCount |= (in.read() & 0xFF) << 16;
        byteCount |= (in.read() & 0xFF) << 8;
        byteCount |= in.read() & 0xFF;

        byte[] bytes = new byte[byteCount];
        in.read(bytes);

        String data = new String(bytes, StandardCharsets.UTF_8);
        String[] lines = data.split("\\r?\\n");

        IPacket packet = createPacket(lines[0]);

        for (int i = 1; i < lines.length; i++)
        {
            String[] prop = lines[i].split("=");
            prop[1] = URLDecoder.decode(prop[1], StandardCharsets.UTF_8);

            packet.setData(prop[0], prop[1]);
        }

        return packet;
    }
}
