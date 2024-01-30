package io.github.daylightnebula.meld.login

import io.github.daylightnebula.meld.login.packets.config.JavaClientInfoPacket
import io.github.daylightnebula.meld.login.packets.config.JavaConfigMessagePacket
import io.github.daylightnebula.meld.login.packets.config.JavaFeatureFlagsPacket
import io.github.daylightnebula.meld.login.packets.config.JavaFinishConfigPacket
import io.github.daylightnebula.meld.login.packets.login.*
import io.github.daylightnebula.meld.server.bedrock
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.java
import io.github.daylightnebula.meld.server.javaPacketID
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConfigKeepAlivePacket
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaNetworkController
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm
import org.cloudburstmc.protocol.bedrock.packet.*
import java.util.*

private val tempUIDStorage = mutableMapOf<IConnection<*>, UUID>()
class LoginBundle: io.github.daylightnebula.meld.server.PacketBundle(
    bedrock(
        RequestNetworkSettingsPacket::class.java.name to { connection, _ ->
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

        LoginPacket::class.java.name to { connection, _ ->
            connection.sendPacket(PlayStatusPacket().apply { status = PlayStatusPacket.Status.LOGIN_SUCCESS })
            connection.sendPacket(ResourcePacksInfoPacket().apply {})
        },

        ClientCacheStatusPacket::class.java.name to { _, _ -> println("WARN ClientCacheStatusPacket is not implemented") },

        ResourcePackClientResponsePacket::class.java.name to { connection, packet ->
            // unpack
            val status = (packet as ResourcePackClientResponsePacket).status

            // process depending on the status given in the input packet
            when (status) {
                ResourcePackClientResponsePacket.Status.HAVE_ALL_PACKS -> {
                    connection.sendPacket(ResourcePackStackPacket().apply {
                        isForcedToAccept = false
                        isExperimentsPreviouslyToggled = false
                        gameVersion = "1.20"
                    })
                }

                ResourcePackClientResponsePacket.Status.COMPLETED -> {
                    // call login event
                    println("Resource pack completed")
                    // https://wiki.vg/Bedrock_Protocol#Start_Game
                }


                ResourcePackClientResponsePacket.Status.NONE -> TODO("Bedrock resourcepack $status not implemented")
                ResourcePackClientResponsePacket.Status.REFUSED -> TODO("Bedrock resourcepack $status not implemented")
                ResourcePackClientResponsePacket.Status.SEND_PACKS -> TODO("Bedrock resourcepack $status not implemented")

                else -> throw NotImplementedError()
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

        JavaStatusStatusPacket::class.java.name to { connection, _ ->
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
            tempUIDStorage[connection] = packet.uuid ?: UUID.randomUUID()

            // respond
            connection.sendPacket(
                JavaLoginSuccessPacket(
                    uuid = tempUIDStorage[connection]!!,
                    username = packet.username
                )
            )

            // move to config state
            connection.state = JavaConnectionState.CONFIG
        },

        JavaConfigKeepAlivePacket::class.java.name to { connection, packet -> },

        JavaClientInfoPacket::class.java.name to { connection, packet ->
//            connection.sendPacket(JavaFeatureFlagsPacket())
            connection.sendPacket(JavaFinishConfigPacket())
        },

        JavaFinishConfigPacket::class.java.name to { connection, packet ->
            connection.state = JavaConnectionState.IN_GAME

            // call login event
            EventBus.callEvent(LoginEvent(connection, tempUIDStorage.remove(connection)!!))
        },

        JavaConfigMessagePacket::class.java.name to { connection, packet ->
            packet as JavaConfigMessagePacket
            when (packet.channel) {
                "minecraft:brand" -> {
                    connection.sendPacket(JavaConfigMessagePacket("minecraft:brand", packet.data))
                }

                else -> println("Unknown plugin message channel ${packet.channel}")
            }
        },
    )
) {
    override fun registerJavaPackets() = io.github.daylightnebula.meld.server.javaPackets(
        javaPacketID(
            JavaHandshakePacket.ID,
            JavaHandshakePacket.TYPE
        ) to { JavaHandshakePacket() },

        javaPacketID(
            JavaStatusStatusPacket.ID,
            JavaStatusStatusPacket.TYPE
        ) to { JavaStatusStatusPacket() },

        javaPacketID(
            JavaStatusPingPacket.ID,
            JavaStatusPingPacket.TYPE
        ) to { JavaStatusPingPacket() },

        javaPacketID(
            JavaInitiateLoginPacket.ID,
            JavaInitiateLoginPacket.TYPE
        ) to { JavaInitiateLoginPacket() },

        javaPacketID(
            JavaConfigKeepAlivePacket.ID,
            JavaConfigKeepAlivePacket.TYPE
        ) to { JavaConfigKeepAlivePacket() },

        javaPacketID(
            JavaClientInfoPacket.ID,
            JavaClientInfoPacket.TYPE
        ) to { JavaClientInfoPacket() },

        javaPacketID(0x01, JavaConnectionState.CONFIG) to { JavaConfigMessagePacket() },
        javaPacketID(0x02, JavaConnectionState.CONFIG) to { JavaFinishConfigPacket() }
    )
}



class LoginEvent(
    val connection: IConnection<*>,
    val uid: UUID
): Event