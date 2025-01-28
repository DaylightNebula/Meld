package io.github.daylightnebula.meld.server.utils

import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.entities.Player


val connectionsToPlayers = hashMapOf<IConnection<*>, Player>()
val IConnection<*>.hasPlayer: Boolean
    get() = connectionsToPlayers[this] != null
var IConnection<*>.player: Player
    get() = connectionsToPlayers[this] ?: throw NotImplementedError("Player not initialized yet!")
    set(value) { connectionsToPlayers[this] = value }