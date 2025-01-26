package io.github.daylightnebula.meld.server.modules

import io.github.daylightnebula.meld.server.PacketBundle
import io.github.daylightnebula.meld.server.generated.JavaServerConfigKeepAlive
import io.github.daylightnebula.meld.server.generated.JavaServerPlayKeepAlive
import io.github.daylightnebula.meld.server.javaPacket
import io.github.daylightnebula.meld.server.javaPackets
import io.github.daylightnebula.meld.server.networking.java.JavaConnection

object KeepAliveBundle: PacketBundle {
    override fun registerJavaPackets() = javaPackets(
        javaPacket(
            creator = JavaServerConfigKeepAlive,
            execute = this::onConfigKeepAlive
        ),

        javaPacket(
            creator = JavaServerPlayKeepAlive,
            execute = this::onPlayKeepAlive
        )
    )

    fun onConfigKeepAlive(connection: JavaConnection, packet: JavaServerConfigKeepAlive) {}
    fun onPlayKeepAlive(connection: JavaConnection, packet: JavaServerPlayKeepAlive) {}
}