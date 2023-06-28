package net.minestom.server.network.packet.client.login;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.network.NetworkBuffer;
import net.minestom.server.network.packet.client.JavaClientPacket;
import net.minestom.server.network.packet.server.login.EncryptionRequestPacketJava;
import net.minestom.server.network.packet.server.login.LoginDisconnectPacketJava;
import net.minestom.server.network.packet.server.login.LoginPluginRequestPacketJava;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static net.minestom.server.network.NetworkBuffer.STRING;
import static net.minestom.server.network.NetworkBuffer.UUID;

public record LoginStartPacket(@NotNull String username,
                               @Nullable UUID profileId) implements JavaClientPacket {
    private static final Component ALREADY_CONNECTED = Component.text("You are already on this server", NamedTextColor.RED);

    public LoginStartPacket(@NotNull NetworkBuffer reader) {
        this(reader.read(STRING), reader.readOptional(UUID));
    }

//    @Override
//    public void process(@NotNull PlayerConnection connection) {
//        final boolean isSocketConnection = connection instanceof PlayerSocketConnection;
//        // Proxy support (only for socket clients) and cache the login username
//        if (isSocketConnection) {
//            PlayerSocketConnection socketConnection = (PlayerSocketConnection) connection;
//            socketConnection.UNSAFE_setLoginUsername(username);
//            // Velocity support
//            if (VelocityProxy.isEnabled()) {
//                final int messageId = ThreadLocalRandom.current().nextInt();
//                final String channel = VelocityProxy.PLAYER_INFO_CHANNEL;
//                // Important in order to retrieve the channel in the response packet
//                socketConnection.addPluginRequestEntry(messageId, channel);
//                connection.sendPacket(new LoginPluginRequestPacketJava(messageId, channel, null));
//                return;
//            }
//        }
//
//        if (MojangAuth.isEnabled() && isSocketConnection) {
//            // Mojang auth
//            if (CONNECTION_MANAGER.getPlayer(username) != null) {
//                connection.sendPacket(new LoginDisconnectPacketJava(ALREADY_CONNECTED));
//                connection.disconnect();
//                return;
//            }
//            final PlayerSocketConnection socketConnection = (PlayerSocketConnection) connection;
//            socketConnection.setConnectionState(ConnectionState.LOGIN);
//
//            final byte[] publicKey = MojangAuth.getKeyPair().getPublic().getEncoded();
//            byte[] nonce = new byte[4];
//            ThreadLocalRandom.current().nextBytes(nonce);
//            socketConnection.setNonce(nonce);
//            socketConnection.sendPacket(new EncryptionRequestPacketJava("", publicKey, nonce));
//        } else {
//            final boolean bungee = BungeeCordProxy.isEnabled();
//            // Offline
//            final UUID playerUuid = bungee && isSocketConnection ?
//                    ((PlayerSocketConnection) connection).gameProfile().uuid() :
//                    CONNECTION_MANAGER.getPlayerConnectionUuid(connection, username);
//            CONNECTION_MANAGER.startPlayState(connection, playerUuid, username, true);
//        }
//    }

    @Override
    public void write(@NotNull NetworkBuffer writer) {
        if (username.length() > 16)
            throw new IllegalArgumentException("Username is not allowed to be longer than 16 characters");
        writer.write(STRING, username);
    }
}
