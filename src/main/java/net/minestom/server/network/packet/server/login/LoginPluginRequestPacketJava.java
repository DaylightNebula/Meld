package net.minestom.server.network.packet.server.login;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.server.JavaServerPacket;
import net.minestom.server.network.packet.server.ServerPacketIdentifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static net.minestom.server.network.NetworkBuffer.*;

public record LoginPluginRequestPacketJava(int messageId, @NotNull String channel,
                                           byte @Nullable [] data) implements JavaServerPacket {
    public LoginPluginRequestPacketJava(@NotNull NetworkBuffer reader) {
        this(reader.read(VAR_INT), reader.read(STRING),
                reader.read(RAW_BYTES));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.write(VAR_INT, messageId);
        writer.write(STRING, channel);
        if (data != null && data.length > 0) {
            writer.write(RAW_BYTES, data);
        }
    }

    @Override
    public int getId() {
        return ServerPacketIdentifier.LOGIN_PLUGIN_REQUEST;
    }
}
