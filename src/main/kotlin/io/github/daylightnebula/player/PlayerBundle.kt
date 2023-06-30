package io.github.daylightnebula.player

import io.github.daylightnebula.*
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.player.packets.JavaReceivePlayerPositionPacket

// TODO handle https://wiki.vg/Protocol#Client_Information
// TODO handle https://wiki.vg/Protocol#Plugin_Message
// TODO handle https://wiki.vg/Protocol#Set_Player_Position_and_Rotation
class PlayerBundle: PacketBundle(
    bedrock(),
    java(
        JavaReceivePlayerPositionPacket::class.java.name to { connection, packet ->
            packet as JavaReceivePlayerPositionPacket
            println("Position: ${packet.x} ${packet.y} ${packet.z} ${packet.onGround}")
        }
    )
) {
    override fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, () -> JavaPacket> = javaPackets(
        javaGamePacket(0x14) to { JavaReceivePlayerPositionPacket() }
    )
}