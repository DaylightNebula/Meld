package io.github.daylightnebula.networking.bedrock

import io.github.daylightnebula.Meld
import io.github.daylightnebula.TestPacketHandler
import io.github.daylightnebula.networking.common.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.core.*
import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.cloudburstmc.netty.channel.raknet.RakChannelFactory
import org.cloudburstmc.netty.channel.raknet.config.RakChannelOption
import org.cloudburstmc.protocol.bedrock.BedrockPong
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.codec.v389.Bedrock_v389
import org.cloudburstmc.protocol.bedrock.codec.v589.Bedrock_v589
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm
import org.cloudburstmc.protocol.bedrock.netty.initializer.BedrockServerInitializer
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacketHandler
import org.cloudburstmc.protocol.bedrock.packet.ClientCacheStatusPacket
import org.cloudburstmc.protocol.bedrock.packet.LoginPacket
import org.cloudburstmc.protocol.bedrock.packet.NetworkSettingsPacket
import org.cloudburstmc.protocol.bedrock.packet.PlayStatusPacket
import org.cloudburstmc.protocol.bedrock.packet.RequestNetworkSettingsPacket
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackClientResponsePacket
import org.cloudburstmc.protocol.bedrock.packet.ResourcePackStackPacket
import org.cloudburstmc.protocol.bedrock.packet.ResourcePacksInfoPacket
import org.cloudburstmc.protocol.bedrock.packet.ServerToClientHandshakePacket
import org.cloudburstmc.protocol.common.PacketSignal
import java.net.InetSocketAddress
import java.util.*
import kotlin.concurrent.thread

object BedrockNetworkController: INetworkController {
    private val address = InetSocketAddress("localhost", Meld.bedrockPort)
    val connections = mutableListOf<BedrockConnection>()

    private val motdResponse = BedrockPong()
        .edition("MCPE")
        .motd("My Server")
        .playerCount(0)
        .maximumPlayerCount(20)
        .gameType("Survival")
        .protocolVersion(Bedrock_v589.CODEC.protocolVersion)

    private val server = ServerBootstrap()
        .channelFactory(RakChannelFactory.server(NioDatagramChannel::class.java))
        .option<ByteBuf>(RakChannelOption.RAK_ADVERTISEMENT, motdResponse.toByteBuf())
        .group(NioEventLoopGroup())
        .childHandler(object : BedrockServerInitializer() {
            override fun initSession(session: BedrockServerSession) {
                // create and store new connection
                val handler = BedrockNetworkPacketHandler()
                connections.add(BedrockConnection(handler, session))

                // update session
                session.codec = Bedrock_v589.CODEC
                session.packetHandler = handler

                println("Init session")
            }

            override fun userEventTriggered(ctx: ChannelHandlerContext?, evt: Any?) {
                println("User event $evt")
            }
        })

    override fun start() {
        server.bind(address).syncUninterruptibly()
        println("Started bedrock network controller")
    }

    override fun stop() { TODO() }
}

class BedrockNetworkPacketHandler(): BedrockPacketHandler {
    lateinit var connection: BedrockConnection

    // handle incoming packets
    override fun handlePacket(packet: BedrockPacket): PacketSignal {
        // if the connection is not logged in yet, handle the packets accordingly
        if (!connection.loggedIn) {
            when(packet) {
                // handle network request
                is RequestNetworkSettingsPacket -> {
                    // send back network settings
                    val output = NetworkSettingsPacket()
                    output.compressionAlgorithm = PacketCompressionAlgorithm.ZLIB
                    output.compressionThreshold = 512
                    connection.sendPacket(output)

                    // update compression settings
                    connection.session.setCompression(PacketCompressionAlgorithm.ZLIB)
                    connection.session.setCompressionLevel(9)
                    connection.compressionEnabled = true
                }

                // handle login request
                is LoginPacket -> {
                    connection.sendPacket(PlayStatusPacket().apply { status = PlayStatusPacket.Status.LOGIN_SUCCESS })
                    connection.sendPacket(ResourcePacksInfoPacket().apply {})
                }

                is ClientCacheStatusPacket -> println("WARN client cache blobs not implemented")

                // handle client resource pack response
                is ResourcePackClientResponsePacket -> handleRPResponse(connection, packet)

                else -> TODO("Unknown pre login packet $packet")
            }
        }

        return PacketSignal.HANDLED
    }

    private fun handleRPResponse(connection: BedrockConnection, packet: ResourcePackClientResponsePacket) {
        // unpack
        val status = packet.status

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

    // handle disconnections
    override fun onDisconnect(reason: String?) {
        println("Disconnect $reason")
        BedrockNetworkController.connections.removeIf { it.packetHandler == this }
    }
}