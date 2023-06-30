package io.github.daylightnebula.player

import io.github.daylightnebula.entities.EntityController
import io.github.daylightnebula.entities.Health
import io.github.daylightnebula.entities.LivingEntity
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.utils.Vector3

// TODO declare recipes packet + crafting data packet
// TODO tags packet (may need more integration)
class Player(
    val connection: IConnection<*>,
    id: Int = EntityController.nextID(),
    position: Vector3 = Vector3(0.0, 0.0, 0.0),
    health: Health = Health(20.0)
): LivingEntity(
    id, position, health
) {
    init {
    }
}