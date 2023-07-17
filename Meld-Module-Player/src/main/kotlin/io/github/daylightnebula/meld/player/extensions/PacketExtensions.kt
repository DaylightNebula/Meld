package io.github.daylightnebula.meld.player.extensions

import io.github.daylightnebula.meld.entities.packets.JavaEntityStatusPacket
import io.github.daylightnebula.meld.player.Player

fun JavaEntityStatusPacket(player: Player) =
    JavaEntityStatusPacket(player.id, 24)