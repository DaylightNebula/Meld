package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.EntityController
import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.entities.Health
import io.github.daylightnebula.meld.entities.metadata.EntityMetadata
import io.github.daylightnebula.meld.entities.metadata.entityMetadata
import io.github.daylightnebula.meld.entities.metadata.itemMetadata
import io.github.daylightnebula.meld.server.utils.ItemContainer
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import java.util.*

class ItemEntity(
    uid: UUID = UUID.randomUUID(),
    id: Int = EntityController.nextID(),
    entityMetadata: EntityMetadata = entityMetadata(),
    val item: ItemContainer? = null,
    val autoPickup: Boolean = true,
    dimension: String = "overworld",
    position: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    startVelocity: Vector3f = Vector3f.ZERO,
    startRotation: Vector2f = Vector2f.ZERO,
): Entity(
    uid, id, EntityType.ITEM, metadata = itemMetadata(entityMetadata, item), dimension, position, startVelocity, startRotation
) {
}