package io.github.daylightnebula.meld.server.networking.bedrock

import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.PacketHandler
import io.github.daylightnebula.meld.server.networking.common.*
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
                Meld.connections.add(BedrockConnection(handler, session))

                // update session
                session.codec = Bedrock_v589.CODEC
                session.packetHandler = handler
            }

            override fun userEventTriggered(ctx: ChannelHandlerContext?, evt: Any?) {
                println("User event $evt")
            }
        })

    override fun start() {
        server.bind(address).syncUninterruptibly()
        println("Started bedrock network controller")
    }

    override fun stop() {}
}

class BedrockNetworkPacketHandler : BedrockPacketHandler {
    lateinit var connection: BedrockConnection

    // handle incoming packets
    override fun handlePacket(packet: BedrockPacket): PacketSignal {
        PacketHandler.handlePacket(connection, packet)
        return PacketSignal.HANDLED
    }

    // handle disconnections
    override fun onDisconnect(reason: String?) {
        println("Disconnect $reason")
        Meld.connections.removeIf { it is BedrockConnection && it.packetHandler == this }
    }
}