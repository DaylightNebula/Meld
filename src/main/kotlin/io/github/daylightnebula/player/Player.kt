package io.github.daylightnebula.player

import io.github.daylightnebula.entities.Health
import io.github.daylightnebula.entities.LivingEntity
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.utils.Vector3

class Player(
    val connection: IConnection<*>,
    position: Vector3 = Vector3(0.0, 0.0, 0.0),
    health: Health = Health(20.0)
): LivingEntity(
    position, health
) {
    init {
        // TODO send login packet
        // TODO feature flags packet
        // TODO difficulty packet
        // TODO abilities packet
        // TODO held item slot packet
        // TODO declare recipes packet (maybe?)
        // TODO tags (may need more integration)
        // TODO entity status (set to 24)
        // TODO set position packet
        // TODO player info packet
        // TODO update view distance packet
        // TODO simulation packet
        // TODO broadcast join event
    }
}