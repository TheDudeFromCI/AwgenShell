package net.whg.asj.conn;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A packet is a small collection of data to send to or recieve from the server. Each packet has a header type to know
 * how the information shoulder be processed, as well as a collection of properties and property values for that packet.
 */
public interface IPacket
{
    /**
     * Gets the name of this packet type. This is the header to know what the information represents.
     * 
     * @return The name of this packet type.
     */
    String getName();

    /**
     * Gets an array of all of the properties which would be assigned by this packet.
     * 
     * @return A static array of all properties which are used by this packet.
     */
    String[] getProperties();

    /**
     * Gets the current data value for a given property.
     * 
     * @param property
     *                     - The property to get the value for. Case sensitive.
     * @return The current value of the given property, or null if this packet has no properties with the given key.
     */
    String getData(String property);
}
