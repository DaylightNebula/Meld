package net.minestom.server.network.packet.client.status;

import net.minestom.server.MinecraftServer;
import net.minestom.server.event.EventDispatcher;
import net.minestom.server.event.server.ClientPingServerEvent;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.client.JavaClientPacket;
import net.minestom.server.network.packet.server.status.PongPacketJava;
import net.minestom.server.network.player.PlayerConnection;
import org.jetbrains.annotations.NotNull;

import static net.minestom.server.network.NetworkBuffer.LONG;

public record PingPacket(long number) implements JavaClientPacket {
    public PingPacket(@NotNull NetworkBuffer reader) {
        this(reader.read(LONG));
    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        writer.write(LONG, number);
    }
}
