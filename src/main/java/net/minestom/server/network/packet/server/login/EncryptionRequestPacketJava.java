package net.minestom.server.network.packet.server.login;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.JavaServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;

import static net.minestom.server.network.NetworkBuffer.BYTE_ARRAY;
import static net.minestom.server.network.NetworkBuffer.STRING;

public record EncryptionRequestPacketJava(@NotNull String serverId,
                                          byte @NotNull [] publicKey,
                                          byte @NotNull [] verifyToken) implements JavaServerPacket {
    public EncryptionRequestPacketJava(@NotNull NetworkBuffer reader) {
        this(reader.read(STRING),
                reader.read(BYTE_ARRAY),
                reader.read(BYTE_ARRAY));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.write(STRING, serverId);
        writer.write(BYTE_ARRAY, publicKey);
        writer.write(BYTE_ARRAY, verifyToken);
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_ENCRYPTION_REQUEST;
    }
}
