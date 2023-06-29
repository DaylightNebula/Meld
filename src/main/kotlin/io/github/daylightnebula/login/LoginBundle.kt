package io.github.daylightnebula.login

import io.github.daylightnebula.*
import io.github.daylightnebula.events.Event
import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaNetworkController
import net.minestom.server.network.packet.client.handshake.HandshakePacket
import net.minestom.server.network.packet.client.login.LoginStartPacket
import net.minestom.server.network.packet.client.status.PingPacket
import net.minestom.server.network.packet.client.status.StatusRequestPacket
import net.minestom.server.network.packet.server.handshake.ResponsePacketJava
import net.minestom.server.network.packet.server.login.LoginSuccessPacketJava
import net.minestom.server.network.packet.server.status.PongPacket
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm
import org.cloudburstmc.protocol.bedrock.packet.*
import java.util.*

class LoginBundle: PacketBundle(
    bedrock(
        RequestNetworkSettingsPacket::class.java.name to { connection, packet ->
            // send back network settings
            connection.sendPacket(NetworkSettingsPacket().apply {
                compressionAlgorithm = PacketCompressionAlgorithm.ZLIB
                compressionThreshold = 512
            })

            // update compression settings
            connection.session.setCompression(PacketCompressionAlgorithm.ZLIB)
            connection.session.setCompressionLevel(9)
            connection.compressionEnabled = true
        },

        LoginPacket::class.java.name to { connection, packet ->
            connection.sendPacket(PlayStatusPacket().apply { status = PlayStatusPacket.Status.LOGIN_SUCCESS })
            connection.sendPacket(ResourcePacksInfoPacket().apply {})
        },

        ClientCacheStatusPacket::class.java.name to { _, _ -> println("WARN ClientCacheStatusPacket is not implemented") },

        ResourcePackClientResponsePacket::class.java.name to { connection, packet ->
            // unpack
            val status = (packet as ResourcePackClientResponsePacket).status

            // process depending on the status given in the input packet
            when(status) {
                ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS -> {
                    connection.sendPacket(ResourcePackStackPacket().apply {
                        isForcedToAccept = false
                        isExperimentsPreviouslyToggled = false
                        gameVersion = "1.20"
                    })
                }

                ResourcePackClientResponsePacket.Status.COMPLETED -> {
                    // call login event
                    EventBus.callEvent(LoginEvent(connection))
                    // https://wiki.vg/Bedrock_Protocol#Start_Game
                }


                ResourcePackClientResponsePacket.Status.NONE -> TODO("$status not implemented")
                ResourcePackClientResponsePacket.Status.REFUSED -> TODO("$status not implemented")
                ResourcePackClientResponsePacket.Status.SEND_PACKS -> TODO("$status not implemented")
            }
        }
    ),

    java(
        HandshakePacket::class.java.name to { connection, packet ->
            packet as HandshakePacket
            when (packet.nextState) {
                1 -> connection.state = JavaConnectionState.STATUS
                2 -> connection.state = JavaConnectionState.LOGIN
                else -> throw IllegalArgumentException("Unknown handshake next state ${packet.nextState}")
            }
        },

        StatusRequestPacket::class.java.name to { connection, packet ->
//            connection.sendPacket(ResponsePacketJava(JavaNetworkController.pingJson().toString()))
        },

        // on ping, send back pong
        PingPacket::class.java.name to { connection, packet ->
            packet as PingPacket
//            connection.sendPacket(PongPacket(packet.number))
        },

        // on initiate login, send back success
        LoginStartPacket::class.java.name to { connection, packet ->
            packet as LoginStartPacket

            // respond
            connection.sendPacket(LoginSuccessPacketJava(packet.profileId ?: UUID.randomUUID(), packet.username, 0))
            connection.state = JavaConnectionState.IN_GAME

            // call login event
            EventBus.callEvent(LoginEvent(connection))
        }
    )
) {
//    override fun registerJavaPackets() = javaPackets(
//        javaPacketID(JavaHandshakePacket.ID, JavaHandshakePacket.TYPE) to { JavaHandshakePacket() },
//        javaPacketID(JavaStatusStatusPacket.ID, JavaStatusStatusPacket.TYPE) to { JavaStatusStatusPacket() },
//        javaPacketID(JavaStatusPingPacket.ID, JavaStatusPingPacket.TYPE) to { JavaStatusPingPacket() },
//        javaPacketID(JavaInitiateLoginPacket.ID, JavaInitiateLoginPacket.TYPE) to { JavaInitiateLoginPacket() }
//    )
}

class LoginEvent(
    val connection: IConnection<*>
): Event