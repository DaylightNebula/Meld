package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.entities.*
import io.github.daylightnebula.meld.entities.metadata.EntityMetadata
import io.github.daylightnebula.meld.entities.metadata.entityMetadata
import io.github.daylightnebula.meld.entities.metadata.itemMetadata
import io.github.daylightnebula.meld.server.utils.ItemContainer
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import java.util.*

//const val TIME_TO_DESPAWN: ULong = 6000u
//
//class ItemEntity(
//    uid: UUID = UUID.randomUUID(),
//    id: Int = EntityController.nextID(),
//    entityMetadata: EntityMetadata = entityMetadata(),
//    val item: ItemContainer? = null,
//    val autoPickup: Boolean = true,
//    val autoDespawn: Boolean = autoPickup,
//    dimension: String = "overworld",
//    position: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
//    startVelocity: Vector3f = Vector3f.ZERO,
//    startRotation: Vector2f = Vector2f.ZERO,
//): Entity(
//    uid, id, EntityType.ITEM, metadata = itemMetadata(entityMetadata, item), dimension, position, startVelocity, startRotation
//), Updatable {
//    var startTick: ULong = 0u
//
//    override fun update(tick: ULong) {
//        // update start tick if necessary
//        if (startTick == (0u).toULong()) startTick = tick
//
//        // handle auto despawn
//        if (autoDespawn && tick - startTick > TIME_TO_DESPAWN) { this.despawn(); return }
//
//        // handle auto pickup
//        if (autoPickup) {
//            // get nearby entities
//            val dimension = W
//        }
//    }
//}