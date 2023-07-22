package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.entities.EntityMoveEvent
import io.github.daylightnebula.meld.entities.packets.JavaRemoveEntitiesPacket
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.events.EventHandler
import io.github.daylightnebula.meld.server.events.EventListener
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.common.DataPacketMode
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.world.chunks.Chunk
import io.github.daylightnebula.meld.world.chunks.getChunkPosition
import io.github.daylightnebula.meld.player.JoinEvent
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.player.PlayerMoveEvent
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.extensions.toChunkPosition
import io.github.daylightnebula.meld.world.packets.JavaChunkPacket
import io.github.daylightnebula.meld.world.packets.JavaSetCenterChunkPacket
import io.github.daylightnebula.meld.world.packets.JavaUnloadChunkPacket
import io.netty.buffer.Unpooled
import jdk.jshell.spi.ExecutionControl.NotImplementedException
import org.cloudburstmc.math.vector.Vector2i
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket

class Dimension(
    val id: String,
    val loadedChunks: HashMap<Vector2i, Chunk> = hashMapOf()
): EventListener {
    @EventHandler
    fun onPlayerJoin(event: JoinEvent) {
        // make sure in this world
        if (event.player.dimensionID != id) return
        println("Received load on join")
        val player = event.player

        // send all loaded chunk events
        getChunksInViewDistance(player.position).forEach { loadChunkForPlayer(player, it) }

        // send center chunk packet
        centerPacket(player)
    }

    @EventHandler
    fun onPlayerMove(event: PlayerMoveEvent) {
        val connection = event.player.connection

        // check if the players chunk has changed
        val oldChunkPos = event.oldPosition.toChunkPosition()
        val newChunkPos = event.position.toChunkPosition()
        if (oldChunkPos == newChunkPos) return

        // get chunks in view distance of old and new chunk positions
        val oldChunks = getChunksInViewDistanceOfChunk(oldChunkPos)
        val newChunks = getChunksInViewDistanceOfChunk(newChunkPos)

        // get chunks to remove
        val toRemove = (0 until oldChunks.size).filter { !newChunks.contains(oldChunks[it]) }
        val toAdd = (0 until newChunks.size).filter { !oldChunks.contains(newChunks[it]) }

        // remove old chunks
        toRemove.forEach { unloadChunkForPlayer(event.player, oldChunks[it]) }

        // spawn new chunks
        toAdd.forEach { loadChunkForPlayer(event.player, newChunks[it]) }

        // send center packet
        centerPacket(event.player)
    }

    @EventHandler
    fun onEntityMove(event: EntityMoveEvent) {
        // check if there is a change in chunk position
        val oldChunkPos = event.oldPosition.toChunkPosition()
        val newChunkPos = event.newPosition.toChunkPosition()

        // move entity to new chunk
        loadedChunks[oldChunkPos]?.entities?.remove(event.entity)
        loadedChunks[newChunkPos]?.entities?.add(event.entity)

        // todo remove for players out of range
    }

    // unload a chunk and its entities for the player
    private fun unloadChunkForPlayer(player: Player, chunk: Chunk) =
        when (val connection = player.connection) {
            is JavaConnection -> {
                connection.sendPacket(JavaUnloadChunkPacket(chunk.position))
                connection.sendPacket(JavaRemoveEntitiesPacket(chunk.entities.map { it.id }))
            }
            is BedrockConnection -> NeedsBedrock()
            else -> throw UnsupportedOperationException()
        }

    private fun loadChunkForPlayer(player: Player, chunk: Chunk) {
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
        when(player.connection) {
            is JavaConnection -> chunk.entities.forEach { e -> e.getSpawnJavaPackets().forEach {
                (player.connection as JavaConnection).sendPacket(it)
            }}
            is BedrockConnection -> NeedsBedrock()
        }

        // broadcast sent chunk event
        EventBus.callEvent(PlayerLoadChunkEvent(player, chunk))
    }

    private fun centerPacket(player: Player) {
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
}

fun dimension(
    name: String,
    vararg loadedChunks: Pair<Vector2i, Chunk>
) = name to Dimension(name, hashMapOf(*loadedChunks))

data class PlayerLoadChunkEvent(val player: Player, val chunk: Chunk): Event
data class PlayerUnloadChunkEvent(val player: Player, val chunk: Chunk): Event