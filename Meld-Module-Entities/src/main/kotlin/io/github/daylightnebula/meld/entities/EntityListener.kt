package io.github.daylightnebula.meld.entities

import io.github.daylightnebula.meld.server.events.EventHandler
import io.github.daylightnebula.meld.server.events.EventListener
import org.json.XMLTokener.entity

class EntityListener: EventListener {
    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        // when an entity is spawned, if it is updatable, add it to the tracking list
        val entity = event.entity
        if (entity is Updatable) synchronized(updatables) { updatables.add(entity) }
    }

    @EventHandler
    fun onEntityDespawn(event: EntityDespawnEvent) {
        // when an entity is despawned, if it is updatable, remove it from the tracking list
        val entity = event.entity
        if (entity is Updatable) synchronized(updatables) { updatables.remove(entity) }
    }
}