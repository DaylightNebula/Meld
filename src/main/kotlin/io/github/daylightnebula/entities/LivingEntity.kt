package io.github.daylightnebula.entities

import io.github.daylightnebula.utils.Vector3

abstract class LivingEntity(
    position: Vector3 = Vector3(0.0, 0.0, 0.0),
    health: Health = Health(1.0)
): Entity(
    position
)

class Health(
    private val maxHealth: Double = 1.0,
    private var health: Double = maxHealth
) {
    fun setHealth(health: Double) { this.health = health }
}