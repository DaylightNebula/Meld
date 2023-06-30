package io.github.daylightnebula.player

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.entities.EntityController
import io.github.daylightnebula.entities.Health
import io.github.daylightnebula.entities.LivingEntity
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.utils.Vector3

// TODO declare recipes packet + crafting data packet
// TODO tags packet (may need more integration)
// TODO update view distance packets
// TODO simulation distance packets
class Player(
    val connection: IConnection<*>,
    id: Int = EntityController.nextID(),
    position: Vector3 = Vector3(0.0, 0.0, 0.0),
    health: Health = Health(20.0)
): LivingEntity(
    id, position, health
) {
    // marks if the player has been sent their join packets
    var joinSent = false
        internal set

    // TODO on set, broadcast packet
    var gameMode: GameMode = GameMode.SURVIVAL
        private set

    // TODO teleport functions
}