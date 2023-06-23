package io.github.daylightnebula

import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioDatagramChannel
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
import org.cloudburstmc.protocol.bedrock.packet.NetworkSettingsPacket
import org.cloudburstmc.protocol.common.PacketSignal
import java.net.InetSocketAddress


fun main() {
    val bindAddress = InetSocketAddress("0.0.0.0", 19132)

    val pong = BedrockPong()
        .edition("MCPE")
        .motd("My Server")
        .playerCount(0)
        .maximumPlayerCount(20)
        .gameType("Survival")
        .protocolVersion(Bedrock_v389.CODEC.protocolVersion)

    ServerBootstrap()
        .channelFactory(RakChannelFactory.server(NioDatagramChannel::class.java))
        .option<ByteBuf>(RakChannelOption.RAK_ADVERTISEMENT, pong.toByteBuf())
        .group(NioEventLoopGroup())
        .childHandler(object : BedrockServerInitializer() {
            override fun initSession(session: BedrockServerSession) {
                // Connection established
                // Make sure to set the packet codec version you wish to use before sending out packets
                session.codec = Bedrock_v589.CODEC
                // Remember to set a packet handler so you receive incoming packets
                session.setPacketHandler(TestPacketHandler(session))

                // By default, the server will use a compatible codec that will read any LoginPacket.
                // After receiving the LoginPacket, you need to set the correct packet codec for the client and continue.
                println("Init session")
            }

            override fun userEventTriggered(ctx: ChannelHandlerContext?, evt: Any?) {
                println("User event $evt")
            }
        })
        .bind(bindAddress)
        .syncUninterruptibly()
}

class TestPacketHandler(val session: BedrockServerSession): BedrockPacketHandler {

    var compressionSet = false

    override fun handlePacket(packet: BedrockPacket?): PacketSignal {
        println("Handle packet $packet ${packet?.packetType}")

        // New since 1.19.30 - sent before login packet
        val algorithm = PacketCompressionAlgorithm.ZLIB

        val responsePacket = NetworkSettingsPacket()
        responsePacket.compressionAlgorithm = algorithm
        responsePacket.compressionThreshold = 512
        session.sendPacketImmediately(responsePacket)

        if (!compressionSet) {
            session.setCompression(algorithm)
            session.setCompressionLevel(9)
            compressionSet = true
        }

        return PacketSignal.HANDLED
    }
}