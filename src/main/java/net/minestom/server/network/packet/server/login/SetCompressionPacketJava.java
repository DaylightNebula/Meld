package net.minestom.server.network.packet.server.login;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.JavaServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

import static net.minestom.server.network.NetworkBuffer.VAR_INT;

public record SetCompressionPacketJava(int threshold) implements JavaServerPacket {
    public SetCompressionPacketJava(@NotNull NetworkBuffer reader) {
        this(reader.read(VAR_INT));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.write(VAR_INT, threshold);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_SET_COMPRESSION;
    }
}
