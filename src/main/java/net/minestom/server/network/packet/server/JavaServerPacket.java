package net.minestom.server.network.packet.server;

import net.minestom.server.network.NetworkBuffer;

public non-sealed interface JavaServerPacket extends NetworkBuffer.Writer, SendablePacket {

    /**
     * Gets the id of this packet.
     * <p>
     * Written in the final buffer header so it needs to match the client id.
     *
     * @return the id of this packet
     */
    int getId();
}
