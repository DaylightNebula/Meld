package io.github.daylightnebula.meld.server.modules.entities

import io.github.daylightnebula.meld.server.entities.EntityDespawnEvent
import io.github.daylightnebula.meld.server.entities.EntitySpawnEvent
import io.github.daylightnebula.meld.server.entities.Updatable
import io.github.daylightnebula.meld.server.entities.updatables
import io.github.daylightnebula.meld.server.events.EventExecutor
import io.github.daylightnebula.meld.server.events.EventListener

class EntityListener: EventListener {
    override val executors: List<EventExecutor<*, *>> = listOf(
        EventExecutor(EntitySpawnEvent.Companion, this::onEntitySpawn),
        EventExecutor(EntityDespawnEvent.Companion, this::onEntityDespawn)
    )

    fun onEntitySpawn(event: EntitySpawnEvent) {
        // when an entity is spawned, if it is updatable, add it to the tracking list
        val entity = event.entity
        if (entity is Updatable) updatables.add(entity)
    }

    fun onEntityDespawn(event: EntityDespawnEvent) {
        // when an entity is despawned, if it is updatable, remove it from the tracking list
        val entity = event.entity
        if (entity is Updatable) updatables.remove(entity)
    }
}