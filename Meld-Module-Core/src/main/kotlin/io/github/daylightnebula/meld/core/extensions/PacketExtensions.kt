package io.github.daylightnebula.meld.core.extensions

import io.github.daylightnebula.meld.core.player.Player
import io.github.daylightnebula.meld.entities.packets.JavaEntityStatusPacket

fun JavaEntityStatusPacket(player: Player) =
    JavaEntityStatusPacket(player.id, 24)