package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.entities.packets.JavaRemoveEntitiesPacket
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.common.DataPacketMode
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.world.chunks.Chunk
import io.github.daylightnebula.meld.world.chunks.getChunkPosition
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.extensions.toChunkPosition
import io.github.daylightnebula.meld.world.packets.JavaChunkPacket
import io.github.daylightnebula.meld.world.packets.JavaSetCenterChunkPacket
import io.github.daylightnebula.meld.world.packets.JavaUnloadChunkPacket
import io.netty.buffer.Unpooled
import org.cloudburstmc.math.vector.Vector2i
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket

class Dimension(
    val id: String,
    val loadedChunks: HashMap<Vector2i, Chunk> = hashMapOf()
) {
    // unload a chunk and its entities for the player
    internal fun unloadChunkForPlayer(player: Player, chunk: Chunk) {
        val connection = player.connection

        // unload chunk
        when (connection) {
            is JavaConnection -> {
                connection.sendPacket(JavaUnloadChunkPacket(chunk.position))
            }
            is BedrockConnection -> NeedsBedrock()
        }

        // remove player as watcher of all entities
        chunk.entities.forEach { e -> e.removeWatcher(player.connection) }
    }

    internal fun loadChunkForPlayer(player: Player, chunk: Chunk) {
        // send packet based on connection type
        when (player.connection) {
            is JavaConnection -> (player.connection as JavaConnection).sendPacket(JavaChunkPacket(chunk))
            is BedrockConnection -> (player.connection as BedrockConnection).sendPacket(LevelChunkPacket().apply {
                // update basic values of packet
                subChunksLength = 24
                isCachingEnabled = false
                chunkX = chunk.position.x
                chunkZ = chunk.position.y

                // serialize data
                val writer = ByteWriter(0x00, DataPacketMode.BEDROCK)
                chunk.writeBedrock(writer)
                data = Unpooled.wrappedBuffer(writer.getRawData())
            })
        }

        // spawn entities
        chunk.entities.forEach { e -> e.addWatcher(player.connection) }

        // broadcast sent chunk event
        EventBus.callEvent(PlayerLoadChunkEvent(player, chunk))
    }

    internal fun centerPacket(player: Player) {
        // get center positions
        val chunkPosition = player.getChunkPosition()

        // send packet based on connection type
        when (player.connection) {
            is JavaConnection -> {
                (player.connection as JavaConnection).sendPacket(JavaSetCenterChunkPacket(chunkPosition.x, chunkPosition.y))
            }
            else -> {} // throw IllegalArgumentException("No center packet for bedrock connections")
        }
    }
    fun getChunksInViewDistance(location: Vector3f): MutableList<Chunk> = getChunksInViewDistanceOfChunk(location.toChunkPosition())
    fun getChunksInViewDistanceOfChunk(chunkPos: Vector2i): MutableList<Chunk> {
        // return chunks between min and max chunk
        val output = mutableListOf<Chunk>()
        (chunkPos.x - Meld.viewDistance .. chunkPos.x + Meld.viewDistance).forEach { x ->
            (chunkPos.y - Meld.viewDistance .. chunkPos.y + Meld.viewDistance).forEach { y ->
                loadedChunks[Vector2i.from(x, y)]?.let { output.add(it) }
            }
        }
        return output
    }

    data class ChunkDiffs(val oldOnly: List<Chunk>, val newOnly: List<Chunk>)
    fun getDiffChunks(oldChunkPos: Vector2i, newChunkPos: Vector2i): ChunkDiffs {
        // skip if the same
        if (oldChunkPos == newChunkPos) return ChunkDiffs(listOf(), listOf())

        // get chunks in view distance of old and new chunk positions
        val oldChunks = getChunksInViewDistanceOfChunk(oldChunkPos)
        val newChunks = getChunksInViewDistanceOfChunk(newChunkPos)

        // get chunks to remove
        val toRemove = (0 until oldChunks.size).filter { !newChunks.contains(oldChunks[it]) }
        val toAdd = (0 until newChunks.size).filter { !oldChunks.contains(newChunks[it]) }

        // response
        return ChunkDiffs(toRemove.map { oldChunks[it] }, toAdd.map { newChunks[it] })
    }
}

fun dimension(
    name: String,
    vararg loadedChunks: Pair<Vector2i, Chunk>
) = name to Dimension(name, hashMapOf(*loadedChunks))

data class PlayerLoadChunkEvent(val player: Player, val chunk: Chunk): Event
data class PlayerUnloadChunkEvent(val player: Player, val chunk: Chunk): Event