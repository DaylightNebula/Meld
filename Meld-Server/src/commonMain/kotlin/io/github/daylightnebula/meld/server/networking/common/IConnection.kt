package io.github.daylightnebula.meld.server.networking.common

interface IConnection<T: Any> {
    fun sendPacket(packet: T)
}

interface Packet