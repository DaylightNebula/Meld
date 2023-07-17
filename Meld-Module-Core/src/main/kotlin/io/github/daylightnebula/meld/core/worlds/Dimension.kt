package io.github.daylightnebula.meld.core.worlds

import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.events.EventHandler
import io.github.daylightnebula.meld.server.events.EventListener
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.common.DataPacketMode
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.core.player.JoinEvent
import io.github.daylightnebula.meld.core.player.Player
import io.github.daylightnebula.meld.core.worlds.chunks.Chunk
import io.github.daylightnebula.meld.core.worlds.chunks.packets.JavaChunkPacket
import io.github.daylightnebula.meld.core.worlds.chunks.packets.JavaSetCenterChunkPacket
import io.github.daylightnebula.meld.core.worlds.chunks.getChunkPosition
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import org.cloudburstmc.math.vector.Vector2i
import org.cloudburstmc.protocol.bedrock.packet.LevelChunkPacket
import java.nio.ByteBuffer

// TODO when player moves, check if we need to load and unload chunks
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
        val chunkPos = player.getChunkPosition()
        val radius = Meld.viewDistance / 2
        ((chunkPos.x - radius) .. (chunkPos.x + radius)).forEach { chunkX ->
            ((chunkPos.y - radius) .. (chunkPos.y + radius)).forEach { chunkZ ->
                loadedChunks[Vector2i.from(chunkX, chunkZ)]?.let {
                    loadChunkForPlayer(player, it)
                }
            }
        }

        // send center chunk packet
        centerPacket(player)
    }

    private fun loadChunkForPlayer(player: Player, chunk: Chunk) {
        // send packet based on connection type
        when (player.connection) {
            is JavaConnection -> player.connection.sendPacket(JavaChunkPacket(chunk))
            is BedrockConnection -> player.connection.sendPacket(LevelChunkPacket().apply {
                // update basic values of packet
                subChunksLength = 24
                isCachingEnabled = false
                chunkX = chunk.chunkX
                chunkZ = chunk.chunkY

                // serialize data
                val writer = ByteWriter(0x00, DataPacketMode.BEDROCK)
                chunk.writeBedrock(writer)
                data = Unpooled.wrappedBuffer(writer.getRawData())
            })
        }

        // spawn entities
        when(player.connection) {
            is JavaConnection -> chunk.entities.forEach { e -> e.getSpawnJavaPackets().forEach { player.connection.sendPacket(it) } }
            is BedrockConnection -> TODO()
        }

        // broadcast sent chunk event
        EventBus.callEvent(PlayerSentChunkEvent(player, chunk))
    }

    private fun centerPacket(player: Player) {
        // get center positions
        val chunkPosition = player.getChunkPosition()

        // send packet based on connection type
        when (player.connection) {
            is JavaConnection -> {
                player.connection.sendPacket(JavaSetCenterChunkPacket(chunkPosition.x, chunkPosition.y))
            }
            else -> {} // throw IllegalArgumentException("No center packet for bedrock connections")
        }
    }
}

fun dimension(
    name: String,
    vararg loadedChunks: Pair<Vector2i, Chunk>
) = name to Dimension(name, hashMapOf(*loadedChunks))

data class PlayerSentChunkEvent(val player: Player, val chunk: Chunk): Event