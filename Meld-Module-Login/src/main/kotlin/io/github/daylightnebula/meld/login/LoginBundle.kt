package io.github.daylightnebula.meld.login

import io.github.daylightnebula.meld.login.packets.config.*
import io.github.daylightnebula.meld.login.packets.login.*
import io.github.daylightnebula.meld.server.*
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConfigKeepAlivePacket
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaNetworkController
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm
import org.cloudburstmc.protocol.bedrock.packet.*
import java.util.*

private val tempUIDStorage = mutableMapOf<IConnection<*>, UUID>()
class LoginBundle: PacketBundle {
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

        javaPacketID(0x02, JavaConnectionState.CONFIG) to { JavaConfigMessagePacket() },
        javaPacketID(0x03, JavaConnectionState.CONFIG) to { JavaFinishConfigPacket() }
    )

    @PacketHandler
    fun onConfigKeepAlive(connection: JavaConnection, packet: JavaConfigKeepAlivePacket) {}

    @PacketHandler
    fun onHandshake(connection: JavaConnection, packet: JavaHandshakePacket) =
        when (packet.nextState) {
            1 -> connection.state = JavaConnectionState.STATUS
            2 -> connection.state = JavaConnectionState.LOGIN
            else -> throw IllegalArgumentException("Unknown handshake next state ${packet.nextState}")
        }

    @PacketHandler
    fun onStatusStatus(connection: JavaConnection, packet: JavaStatusStatusPacket) =
        connection.sendPacket(JavaStatusStatusPacket().apply {
            json = JavaNetworkController.pingJson()
        })

    @PacketHandler
    fun onStatusPing(connection: JavaConnection, packet: JavaStatusPingPacket) = connection.sendPacket(packet)

    @PacketHandler
    fun onInitiateLogin(connection: JavaConnection, packet: JavaInitiateLoginPacket) {
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
    }

    @PacketHandler
    fun onClientInfo(connection: JavaConnection, packet: JavaClientInfoPacket) {
//            connection.sendPacket(JavaFeatureFlagsPacket())
        connection.sendPacket(JavaRegistryDataPacket())
        connection.sendPacket(JavaFinishConfigPacket())
    }

    @PacketHandler
    fun onFinishConfig(connection: JavaConnection, packet: JavaFinishConfigPacket) {
        connection.state = JavaConnectionState.IN_GAME
        EventBus.callEvent(LoginEvent(connection, tempUIDStorage.remove(connection)!!))
    }

    @PacketHandler
    fun onConfigMessage(connection: JavaConnection, packet: JavaConfigMessagePacket) =
        when (packet.channel) {
            "minecraft:brand" -> {
                connection.sendPacket(JavaConfigMessagePacket("minecraft:brand", packet.data))
            }

            else -> println("Unknown plugin message channel ${packet.channel}")
        }

    @PacketHandler
    fun onBedrockRequestNetworkSettings(connection: BedrockConnection, packet: RequestNetworkSettingsPacket) {
        // send back network settings
        connection.sendPacket(NetworkSettingsPacket().apply {
            compressionAlgorithm = PacketCompressionAlgorithm.ZLIB
            compressionThreshold = 512
        })

        // update compression settings
        connection.session.setCompression(PacketCompressionAlgorithm.ZLIB)
        connection.session.setCompressionLevel(9)
        connection.compressionEnabled = true
    }

    @PacketHandler
    fun onBedrockLogin(connection: BedrockConnection, packet: LoginPacket) {
        connection.sendPacket(PlayStatusPacket().apply { status = PlayStatusPacket.Status.LOGIN_SUCCESS })
        connection.sendPacket(ResourcePacksInfoPacket().apply {})
    }

    @PacketHandler
    fun onBedrockClientCacheStatus(connection: BedrockConnection, packet: ClientCacheStatusPacket) =
        println("WARN ClientCacheStatusPacket is not implemented")

    @PacketHandler
    fun onBedrockResourcePackClientResponse(connection: BedrockConnection, packet: ResourcePackClientResponsePacket) {
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
}



class LoginEvent(
    val connection: IConnection<*>,
    val uid: UUID
): Event