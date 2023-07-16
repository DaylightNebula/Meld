package io.github.daylightnebula.meld.server.networking.bedrock

import io.github.daylightnebula.meld.server.networking.common.IConnection
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket

class BedrockConnection(
    val packetHandler: BedrockNetworkPacketHandler,
    val session: BedrockServerSession,
    var compressionEnabled: Boolean = false,
    var loggedIn: Boolean = false
): IConnection<BedrockPacket> {
    init { packetHandler.connection = this }

    override fun sendPacket(packet: BedrockPacket) {
        session.sendPacketImmediately(packet)
    }

    override fun toString(): String {
        return "BedrockConnection(address=${session.socketAddress},compressionEnabled=$compressionEnabled,loggedIn=$loggedIn)"
    }
}