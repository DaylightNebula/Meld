package io.github.daylightnebula.meld.server.modules.world

import io.github.daylightnebula.meld.server.entities.EntityDespawnEvent
import io.github.daylightnebula.meld.server.entities.EntityMoveEvent
import io.github.daylightnebula.meld.server.entities.EntitySpawnEvent
import io.github.daylightnebula.meld.server.entities.Player
import io.github.daylightnebula.meld.server.entities.PlayerMoveEvent
import io.github.daylightnebula.meld.server.events.EventExecutor
import io.github.daylightnebula.meld.server.events.EventListener
import io.github.daylightnebula.meld.server.modules.player.JoinEvent
import io.github.daylightnebula.meld.server.utils.toChunkPosition
import io.github.daylightnebula.meld.server.world.World

class WorldListener: EventListener {
    override val executors: List<EventExecutor<*, *>> = listOf(
        EventExecutor(EntitySpawnEvent, this::onEntitySpawn),
        EventExecutor(EntityDespawnEvent, this::onEntityDespawn),
        EventExecutor(EntityMoveEvent, this::onEntityMove),
        EventExecutor(JoinEvent, this::onPlayerJoin),
        EventExecutor(PlayerMoveEvent, this::onPlayerMove)
    )

    fun onEntitySpawn(event: EntitySpawnEvent) {
        val entity = event.entity

        // get dimension
        val dimension = World.dimensions[entity.dimensionID] ?: return

        // add entity to its chunk
        val chunk = dimension.getChunk(entity.position.toChunkPosition())
        chunk.entities.add(entity)

        // add all nearby players as watchers
        dimension.getChunksInViewDistanceOfChunk(chunk.position).forEach { view ->
            view.entities.filterIsInstance<Player>().forEach {
                entity.addWatcher(it.connection)
            }
        }
    }

    fun onEntityDespawn(event: EntityDespawnEvent) {
        val entity = event.entity

        // get dimension and chunk
        val dimension = World.dimensions[entity.dimensionID] ?: return
        val chunk = dimension.getChunk(entity.position.toChunkPosition())

        // remove from chunk
        chunk.entities.remove(entity)
    }

    fun onPlayerJoin(event: JoinEvent) {
        val player = event.player

        // get dimension
        val dimension = World.dimensions[player.dimensionID] ?: return

        // send all loaded chunk events
        dimension.getChunksInViewDistance(player.position).forEach { dimension.loadChunkForPlayer(player, it) }

        // send center chunk packet
        dimension.centerPacket(player)
    }

    fun onPlayerMove(event: PlayerMoveEvent) {
        val player = event.player

        // get dimension
        val dimension = World.dimensions[player.dimensionID] ?: return

        // get chunk differences
        val chunkDiffs = dimension.getDiffChunks(event.oldPosition.toChunkPosition(), event.position.toChunkPosition())

        // remove old chunks and spawn new ones
        chunkDiffs.oldOnly.forEach { dimension.unloadChunkForPlayer(event.player, it) }
        chunkDiffs.newOnly.forEach { dimension.loadChunkForPlayer(event.player, it) }

        // send center packet
        if (chunkDiffs.oldOnly.isNotEmpty()) dimension.centerPacket(event.player)
    }

    fun onEntityMove(event: EntityMoveEvent) {
        val entity = event.entity

        // get dimension
        val dimension = World.dimensions[entity.dimensionID] ?: return

        // check if there is a change in chunk position
        val oldChunkPos = event.oldPosition.toChunkPosition()
        val newChunkPos = event.newPosition.toChunkPosition()

        // move entity to new chunk
        dimension.getChunk(oldChunkPos).entities.remove(event.entity)
        dimension.getChunk(newChunkPos).entities.add(event.entity)

        // get chunk diffs
        val chunkDiffs =
            dimension.getDiffChunks(event.oldPosition.toChunkPosition(), event.newPosition.toChunkPosition())

        // remove all players in old chunks from watchers
        chunkDiffs.oldOnly.forEach { chunk -> chunk.entities.filterIsInstance<Player>().forEach {
            entity.removeWatcher(it.connection)
        }}

        // add all players in new chunk to watchers
        chunkDiffs.newOnly.forEach { chunk -> chunk.entities.filterIsInstance<Player>().forEach {
            entity.addWatcher(it.connection)
        }}
    }
}