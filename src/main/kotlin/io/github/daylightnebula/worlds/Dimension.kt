package io.github.daylightnebula.worlds

import io.github.daylightnebula.Meld
import io.github.daylightnebula.events.EventHandler
import io.github.daylightnebula.events.EventListener
import io.github.daylightnebula.networking.bedrock.BedrockConnection
import io.github.daylightnebula.networking.java.JavaConnection
import io.github.daylightnebula.player.JoinEvent
import io.github.daylightnebula.player.Player
import io.github.daylightnebula.worlds.chunks.Chunk
import io.github.daylightnebula.worlds.chunks.packets.JavaChunkPacket
import io.github.daylightnebula.worlds.chunks.packets.JavaSetCenterChunkPacket
import io.github.daylightnebula.worlds.chunks.getChunkPosition
import org.cloudburstmc.math.vector.Vector2i

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
            is JavaConnection -> {
                player.connection.sendPacket(JavaChunkPacket(chunk))
            }
            is BedrockConnection -> TODO()
        }
    }

    private fun centerPacket(player: Player) {
        // get center positions
        val chunkPosition = player.getChunkPosition()

        // send packet based on connection type
        when (player.connection) {
            is JavaConnection -> {
                player.connection.sendPacket(JavaSetCenterChunkPacket(chunkPosition.x, chunkPosition.y))
            }
            else -> throw IllegalArgumentException("No center packet for bedrock connections")
        }
    }
}

fun dimension(
    name: String,
    vararg loadedChunks: Pair<Vector2i, Chunk>
) = name to Dimension(name, hashMapOf(*loadedChunks))