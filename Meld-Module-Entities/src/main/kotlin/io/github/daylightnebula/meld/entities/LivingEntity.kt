package io.github.daylightnebula.meld.entities

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.entities.metadata.EntityMetadata
import io.github.daylightnebula.meld.entities.metadata.entityMetadata
import java.util.*

abstract class LivingEntity(
    uid: UUID = UUID.randomUUID(),
    id: Int = EntityController.nextID(),
    type: EntityType = EntityType.ARMOR_STAND,
    metadata: EntityMetadata = entityMetadata(),
    dimension: String = "overworld",
    position: Float3 = Float3(),
    startVelocity: Float3 = Float3(),
    startRotation: Float2 = Float2(),
    var headYaw: Float = 0f,
    val health: Health = Health(1.0)
): Entity(
    uid, id, type, metadata, dimension, position, startVelocity, startRotation
)

class Health(
    private val maxHealth: Double = 1.0,
    private var health: Double = maxHealth
) {
    fun setHealth(health: Double) { this.health = health }
}