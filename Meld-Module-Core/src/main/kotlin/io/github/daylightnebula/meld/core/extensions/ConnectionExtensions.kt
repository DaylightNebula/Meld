package io.github.daylightnebula.meld.core.extensions

import io.github.daylightnebula.meld.core.player.Player
import io.github.daylightnebula.meld.server.networking.common.IConnection


val connectionsToPlayers = hashMapOf<IConnection<*>, Player>()
var IConnection<*>.player: Player
    get() = connectionsToPlayers[this] ?: throw NotImplementedError("Player not initialized yet!")
    set(value) { connectionsToPlayers[this] = value }