package io.github.daylightnebula.meld.server.networking.common

import io.github.daylightnebula.meld.server.player.Player

interface IConnection<T: Any> {
    var player: Player?
    fun sendPacket(packet: T)
}