package io.github.daylightnebula.player

import io.github.daylightnebula.*
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaPacket

class PlayerBundle: PacketBundle(
    bedrock(),
    java(
        JavaSetPlayerPosition::class.java.name to { connection, packet ->
            packet as JavaSetPlayerPosition
            println("Position: ${packet.x} ${packet.y} ${packet.z} ${packet.onGround}")
        }
    )
) {
    override fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, () -> JavaPacket> = javaPackets(
        javaGamePacket(0x14) to { JavaSetPlayerPosition() }
    )
}