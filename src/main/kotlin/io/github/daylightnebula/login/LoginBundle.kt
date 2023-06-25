package io.github.daylightnebula.login

import io.github.daylightnebula.*
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaNetworkController
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
                    // TODO add "logged in" connection reference here
                    // TODO broadcast pre login complete event here
                    // https://wiki.vg/Bedrock_Protocol#Start_Game
                    println("TODO bedrock logged in")
                }


                ResourcePackClientResponsePacket.Status.NONE -> TODO("$status not implemented")
                ResourcePackClientResponsePacket.Status.REFUSED -> TODO("$status not implemented")
                ResourcePackClientResponsePacket.Status.SEND_PACKS -> TODO("$status not implemented")
            }
        }
    ),

    java(
        JavaHandshakePacket::class.java.name to { connection, packet ->
            packet as JavaHandshakePacket
            when (packet.nextState) {
                1 -> connection.state = JavaConnectionState.STATUS
                2 -> connection.state = JavaConnectionState.LOGIN
                else -> throw IllegalArgumentException("Unknown handshake next state ${packet.nextState}")
            }
        },

        JavaStatusStatusPacket::class.java.name to { connection, packet ->
            connection.sendPacket(JavaStatusStatusPacket().apply {
                json = JavaNetworkController.pingJson()
            })
        },

        // on ping, send back pong
        JavaStatusPingPacket::class.java.name to { connection, packet ->
            connection.sendPacket(packet)
        },

        // on initiate login, send back success
        JavaInitiateLoginPacket::class.java.name to { connection, packet ->
            packet as JavaInitiateLoginPacket
            connection.sendPacket(JavaLoginSuccessPacket(uuid = packet.uuid ?: UUID.randomUUID(), username = packet.username))
        }
    )
) {
    override fun registerJavaPackets() = javaPackets(
        javaPacketID(JavaHandshakePacket.ID, JavaHandshakePacket.TYPE) to { JavaHandshakePacket() },
        javaPacketID(JavaStatusStatusPacket.ID, JavaStatusStatusPacket.TYPE) to { JavaStatusStatusPacket() },
        javaPacketID(JavaStatusPingPacket.ID, JavaStatusPingPacket.TYPE) to { JavaStatusPingPacket() },
        javaPacketID(JavaInitiateLoginPacket.ID, JavaInitiateLoginPacket.TYPE) to { JavaInitiateLoginPacket() }
    )
}