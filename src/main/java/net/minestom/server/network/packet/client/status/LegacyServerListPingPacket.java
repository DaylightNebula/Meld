package net.minestom.server.network.packet.client.status;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.client.JavaClientPacket;
import org.jetbrains.annotations.NotNull;

import static net.minestom.server.network.NetworkBuffer.BYTE;

public record LegacyServerListPingPacket(byte payload) implements JavaClientPacket {
    public LegacyServerListPingPacket(@NotNull NetworkBuffer reader) {
        this(reader.read(BYTE));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.write(BYTE, payload);
    }
}
