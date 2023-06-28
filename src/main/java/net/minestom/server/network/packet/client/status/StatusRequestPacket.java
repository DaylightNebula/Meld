package net.minestom.server.network.packet.client.status;

import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.client.JavaClientPacket;
import org.jetbrains.annotations.NotNull;

public record StatusRequestPacket() implements JavaClientPacket {
    public StatusRequestPacket(@NotNull NetworkBuffer reader) {
        this();
    }

//    @Override
//    public void process(@NotNull PlayerConnection connection) {
//        final ServerListPingType pingVersion = ServerListPingType.fromModernProtocolVersion(connection.getProtocolVersion());
//        final ServerListPingEvent statusRequestEvent = new ServerListPingEvent(connection, pingVersion);
//        EventDispatcher.callCancellable(statusRequestEvent, () ->
//                connection.sendPacket(new ResponsePacketJava(pingVersion.getPingResponse(statusRequestEvent.getResponseData()))));
//    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        // Empty
    }
}
