package io.github.daylightnebula.networking.common

import io.github.daylightnebula.player.Player

interface IConnection<T: Any> {
    var player: Player?
    fun sendPacket(packet: T)
}