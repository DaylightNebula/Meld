package io.github.daylightnebula.meld.world

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.common.DataPacketMode
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.world.chunks.Chunk
import io.github.daylightnebula.meld.world.chunks.getChunkPosition
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.extensions.toChunkPosition
import io.github.daylightnebula.meld.world.chunks.toSectionPosition
import io.github.daylightnebula.meld.world.packets.JavaChunkPacket
import io.github.daylightnebula.meld.world.packets.JavaSetCenterChunkPacket
import io.github.daylightnebula.meld.world.packets.JavaUnloadChunkPacket
import kotlin.math.roundToInt
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class Dimension(
    val id: String,
    private val loadedChunks: MutableMap<Float2, Chunk> = hashMapOf()
) {
    fun getChunk(position: Float2): Chunk {
        // attempt to get a chunk
        var chunk = loadedChunks[position]

        // if no chunk found, create one
        if (chunk == null) {
            val event = ChunkCreateEvent(Chunk())
            EventBus.callEvent(event)
            chunk = event.chunk
            loadedChunks[position] = chunk
        }

        // return chunk
        return chunk
    }

    fun getChunks(): Collection<Chunk> = loadedChunks.values

    // unload a chunk and its entities for the player
    internal fun unloadChunkForPlayer(player: Player, chunk: Chunk) {
        val connection = player.connection

        // unload chunk
        when (connection) {
            is JavaConnection -> {
                connection.sendPacket(JavaUnloadChunkPacket(chunk.position))
            }
//          BEDROCK  is BedrockConnection -> NeedsBedrock()
        }

        // remove player as watcher of all entities
        chunk.entities.forEach { e -> e.removeWatcher(player.connection) }
    }

    internal fun loadChunkForPlayer(player: Player, chunk: Chunk) {
        // send packet based on connection type
        when (player.connection) {
            is JavaConnection -> (player.connection as JavaConnection).sendPacket(JavaChunkPacket(chunk))
//          BEDROCK  is BedrockConnection -> (player.connection as BedrockConnection).sendPacket(LevelChunkPacket().apply {
//                // update basic values of packet
//                subChunksLength = 24
//                isCachingEnabled = false
//                chunkX = chunk.position.x
//                chunkZ = chunk.position.y
//
//                // serialize data
//                val writer = ByteWriter(0x00, DataPacketMode.BEDROCK)
//                chunk.writeBedrock(writer)
//                data = Unpooled.wrappedBuffer(writer.getRawData())
//            })
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
                (player.connection as JavaConnection).sendPacket(JavaSetCenterChunkPacket(chunkPosition.x.toInt(), chunkPosition.y.toInt()))
            }
            else -> {} // throw IllegalArgumentException("No center packet for bedrock connections")
        }
    }
    fun getChunksInViewDistance(location: Float3): MutableList<Chunk> = getChunksInViewDistanceOfChunk(location.toChunkPosition())
    fun getChunksInViewDistanceOfChunk(chunkPos: Float2): MutableList<Chunk> {
        // return chunks between min and max chunk
        val output = mutableListOf<Chunk>()
        ((chunkPos.x - Meld.viewDistance).roundToInt() .. (chunkPos.x + Meld.viewDistance).roundToInt()).forEach { x ->
            ((chunkPos.y - Meld.viewDistance).roundToInt() .. (chunkPos.y + Meld.viewDistance).roundToInt()).forEach { y ->
                output.add(getChunk(Float2(x.toFloat(), y.toFloat())))
            }
        }
        return output
    }

    data class ChunkDiffs(val oldOnly: List<Chunk>, val newOnly: List<Chunk>)
    fun getDiffChunks(oldChunkPos: Float2, newChunkPos: Float2): ChunkDiffs {
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

    // get and set block functions
    fun getBlock(position: Float3) = getChunk(position.toChunkPosition()).getBlock(position)
    fun setBlock(position: Float3, blockID: Int) = getChunk(position.toChunkPosition()).setBlock(position, blockID)

    // fill function
    fun fillBlocks(from: Float3, to: Float3, blockID: Int) {
        // get to and from chunk position
        val fromChunk = from.toChunkPosition()
        val toChunk = to.toChunkPosition()

        // loop through all chunks in fill
        (fromChunk.x.toInt() .. toChunk.x.toInt()).forEach { x ->
            (fromChunk.y.toInt() .. toChunk.y.toInt()).forEach { y ->
                // get highest and lowest x and z positions relative to chunk
                val lowX = if (fromChunk.x.toInt() == x) from.x.toSectionPosition() else 0
                val highX = if (toChunk.x.toInt() == x) to.x.toSectionPosition() else 16
                val lowZ = if (fromChunk.y.toInt() == y) from.y.toSectionPosition() else 0
                val highZ = if (toChunk.y.toInt() == y) to.y.toSectionPosition() else 16

                // call fill function on chunk
                getChunk(Float2(x.toFloat(), y.toFloat()))
                    .fill(Float3(lowX.toFloat(), from.y, lowZ.toFloat()), Float3(highX.toFloat(), to.y, highZ.toFloat()), blockID)
            }
        }
    }
    fun clearBlocks(from: Float3, to: Float3) = fillBlocks(from, to, 0)
}

fun dimension(
    name: String,
    vararg loadedChunks: Pair<Float2, Chunk>
) = name to Dimension(name, hashMapOf(*loadedChunks))

@OptIn(ExperimentalUuidApi::class)
data class PlayerLoadChunkEvent(val player: Player, val chunk: Chunk): Event {
    companion object: Event.Data<PlayerLoadChunkEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerLoadChunkEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerUnloadChunkEvent(val player: Player, val chunk: Chunk): Event {
    companion object: Event.Data<PlayerUnloadChunkEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerUnloadChunkEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class ChunkCreateEvent(val chunk: Chunk): Event {
    companion object: Event.Data<ChunkCreateEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(ChunkCreateEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}
