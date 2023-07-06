package io.github.daylightnebula.entities

import org.cloudburstmc.math.vector.Vector3f

abstract class LivingEntity(
    id: Int = EntityController.nextID(),
    position: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    health: Health = Health(1.0)
): Entity(
    id, position
)

class Health(
    private val maxHealth: Double = 1.0,
    private var health: Double = maxHealth
) {
    fun setHealth(health: Double) { this.health = health }
}