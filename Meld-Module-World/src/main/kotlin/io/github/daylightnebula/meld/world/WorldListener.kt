package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.entities.EntityDespawnEvent
import io.github.daylightnebula.meld.entities.EntityMoveEvent
import io.github.daylightnebula.meld.entities.EntitySpawnEvent
import io.github.daylightnebula.meld.entities.packets.JavaRemoveEntitiesPacket
import io.github.daylightnebula.meld.entities.packets.JavaSpawnEntityPacket
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
import org.json.XMLTokener.entity

class WorldListener: EventListener {
    @EventHandler
    fun onEntitySpawn(event: EntitySpawnEvent) {
        val entity = event.entity

        // get dimension
        val dimension = World.dimensions[entity.dimensionID] ?: return

        // add entity to its chunk
        val chunk = dimension.loadedChunks[entity.position.toChunkPosition()] ?: return
        chunk.entities.add(entity)

        // spawn for all players in view distance
        val javaSpawnPackets = entity.getSpawnJavaPackets()
        dimension.getChunksInViewDistanceOfChunk(chunk.position).forEach { chunk ->
            chunk.entities.filter { it is Player && it != entity }.forEach { player ->
                player as Player
                when (player.connection) {
                    is JavaConnection -> {
                        javaSpawnPackets.forEach { (player.connection as JavaConnection).sendPacket(it) }
                    }
                    is BedrockConnection -> NeedsBedrock()
                }
            }
        }
    }

    @EventHandler
    fun onEntityDespawn(event: EntityDespawnEvent) {
        val entity = event.entity

        // get dimension and chunk
        val dimension = World.dimensions[entity.dimensionID] ?: return
        val chunk = dimension.loadedChunks[entity.position.toChunkPosition()] ?: return

        // despawn for all players in range
        val javaRemovePacket = JavaRemoveEntitiesPacket(listOf(entity.id))
        dimension.getChunksInViewDistanceOfChunk(chunk.position).forEach { chunk ->
            chunk.entities.filter { it is Player && it != entity }.forEach { player ->
                player as Player
                when (player.connection) {
                    is JavaConnection -> (player.connection as JavaConnection).sendPacket(javaRemovePacket)
                    is BedrockConnection -> NeedsBedrock()
                }
            }
        }

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

        // de-spawn for all players in old chunks and spawn for all players in new chunks
        val javaDeSpawnPacket = listOf(JavaRemoveEntitiesPacket(listOf(entity.id)))
        val javaSpawnPacket = entity.getSpawnJavaPackets()
        sendPacketsToAllPlayersInChunkList(entity as? Player, chunkDiffs.oldOnly, javaDeSpawnPacket)
        sendPacketsToAllPlayersInChunkList(entity as? Player, chunkDiffs.newOnly, javaSpawnPacket)
    }

    private fun sendPacketsToAllPlayersInChunkList(bannedPlayer: Player?, chunks: List<Chunk>, javaPackets: List<JavaPacket>, /*packet: BedrockPacket*/) {
        chunks.forEach { chunk -> chunk.entities.filterIsInstance<Player>().filter { it != bannedPlayer }.forEach {
            it as Player
            when (it.connection) {
                is JavaConnection -> javaPackets.forEach { packet -> (it.connection as JavaConnection).sendPacket(packet) }
                is BedrockConnection -> NeedsBedrock()
            }
        }}
    }
}