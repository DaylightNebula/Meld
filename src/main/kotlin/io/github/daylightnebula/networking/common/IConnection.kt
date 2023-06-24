package io.github.daylightnebula.networking.common

interface IConnection<T: Any> {
    fun sendPacket(packet: T)
}