package net.whg.asj.packets;

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

    /**
     * Assigns a value to this packet directly. This method is mainly used when loading a packet from a stream.
     * 
     * @param property
     *                     - The property to assign. Case sensitive.
     * @param value
     *                     - The value of the property.
     */
    void setData(String property, String value);

    /**
     * Called on the main thread to handle this packet when it is received from the server.
     */
    void handle();
}
