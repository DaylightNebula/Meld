package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.entities.EntityDespawnEvent
import io.github.daylightnebula.meld.entities.EntityMoveEvent
import io.github.daylightnebula.meld.entities.EntitySpawnEvent
import io.github.daylightnebula.meld.entities.packets.JavaRemoveEntitiesPacket
import io.github.daylightnebula.meld.player.JoinEvent
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.player.PlayerMoveEvent
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.EventHandler
import io.github.daylightnebula.meld.server.events.EventListener
import io.github.daylightnebula.meld.server.extensions.toChunkPosition
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.world.chunks.Chunk

class WorldListener: EventListener {
    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        val entity = event.entity

        // get dimension
        val dimension = World.dimensions[entity.dimensionID] ?: return

        // add entity to its chunk
        val chunk = dimension.loadedChunks[entity.position.toChunkPosition()] ?: return
        chunk.entities.add(entity)

        // add all nearby players as watchers
        dimension.getChunksInViewDistanceOfChunk(chunk.position).forEach { chunk ->
            chunk.entities.filterIsInstance<Player>().forEach {
                entity.addWatcher(it.connection)
            }
        }
    }

    @EventHandler
    fun onEntityDespawn(event: EntityDespawnEvent) {
        val entity = event.entity

        // get dimension and chunk
        val dimension = World.dimensions[entity.dimensionID] ?: return
        val chunk = dimension.loadedChunks[entity.position.toChunkPosition()] ?: return

        // remove from chunk
        chunk.entities.remove(entity)
    }

    @EventHandler
    fun onPlayerJoin(event: JoinEvent) {
        val player = event.player

        // get dimension
        val dimension = World.dimensions[player.dimensionID] ?: return

        // send all loaded chunk events
        dimension.getChunksInViewDistance(player.position).forEach { dimension.loadChunkForPlayer(player, it) }

        // send center chunk packet
        dimension.centerPacket(player)
    }

    @EventHandler
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
        dimension.centerPacket(event.player)
    }

    @EventHandler
    fun onEntityMove(event: EntityMoveEvent) {
        val entity = event.entity

        // get dimension
        val dimension = World.dimensions[entity.dimensionID] ?: return

        // check if there is a change in chunk position
        val oldChunkPos = event.oldPosition.toChunkPosition()
        val newChunkPos = event.newPosition.toChunkPosition()

        // move entity to new chunk
        dimension.loadedChunks[oldChunkPos]?.entities?.remove(event.entity)
        dimension.loadedChunks[newChunkPos]?.entities?.add(event.entity)

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