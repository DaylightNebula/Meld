package io.github.daylightnebula.meld.server.entities

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.generated.EntityType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
abstract class LivingEntity(
    uid: Uuid = Uuid.random(),
    id: Int = EntityIDs.nextID(),
    type: EntityType = EntityType.ARMOR_STAND,
    metadata: EntityMetadata = EntityMetadata(),
    dimension: String = "overworld",
    position: Float3 = Float3(),
    startVelocity: Float3 = Float3(),
    startRotation: Float2 = Float2(),
    var headYaw: Float = 0f,
    val health: Health = Health(1.0)
): Entity(
    uid, id, type, metadata,
    dimension, position, startVelocity,
    startRotation
)

class Health(
    private val maxHealth: Double = 1.0,
    private var health: Double = maxHealth
) {
    fun setHealth(health: Double) { this.health = health }
}