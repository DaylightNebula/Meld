package io.github.daylightnebula.meld.core.entities

import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import java.util.*

abstract class LivingEntity(
    uid: UUID = UUID.randomUUID(),
    id: Int = EntityController.nextID(),
    type: EntityType = EntityType.ARMOR_STAND,
    dimension: String = "overworld",
    position: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    startVelocity: Vector3f = Vector3f.ZERO,
    startRotation: Vector2f = Vector2f.ZERO,
    var headYaw: Float = 0f,
    val health: Health = Health(1.0)
): Entity(
    uid, id, type, dimension, position, startVelocity, startRotation
)

class Health(
    private val maxHealth: Double = 1.0,
    private var health: Double = maxHealth
) {
    fun setHealth(health: Double) { this.health = health }
}