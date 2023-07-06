package io.github.daylightnebula.player.extensions

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.networking.java.JavaConnection
import io.github.daylightnebula.player.Player
import io.github.daylightnebula.player.PlayerBlockAction
import io.github.daylightnebula.worlds.World
import io.github.daylightnebula.worlds.chunks.*
import org.cloudburstmc.math.vector.Vector3i
import kotlin.math.floor

fun Player.handleBlockAction(action: PlayerBlockAction, position: Vector3i, face: Byte) {
    when(action) {
        PlayerBlockAction.START_DIGGING -> handleStartDiggingAction(position, face)
        PlayerBlockAction.CANCELLED_DIGGING -> TODO()
        PlayerBlockAction.FINISHED_DIGGING -> TODO()
        PlayerBlockAction.DROP_ITEM_STACK -> TODO()
        PlayerBlockAction.DROP_ITEM -> TODO()
        PlayerBlockAction.SHOOT_ARROW_FINISH_EATING -> TODO()
        PlayerBlockAction.SWAP_ITEM_IN_HAND -> TODO()
    }
}

fun Player.handleStartDiggingAction(position: Vector3i, face: Byte) {
    when(gameMode) {
        GameMode.CREATIVE -> {
            // get a chunk for the position
            val chunkPos = position.toChunkPosition()
            val chunk = World.dimensions[dimensionID]?.loadedChunks?.get(chunkPos)
                ?: throw RuntimeException("No chunk $chunkPos found for start digging action")

            // get section
            val section = chunk.sections[position.y.toSectionID()]
            val palette = section.blockPalette as FlexiblePalette
            palette.set(
                Vector3i.from(
                    floor((position.x % 16).inc16IfNegative().toDouble()).toInt(),
                    floor((position.y % 16).inc16IfNegative().toDouble()).toInt(),
                    floor((position.z % 16).inc16IfNegative().toDouble()).toInt(),
                ), 0
            ) // TODO fix, may check that chunk position is accurate to minecraft f3 and protocol site

            when (connection) {
                is JavaConnection -> connection.sendPacket(JavaChunkPacket(chunk))
                else -> TODO()
            }

            // TODO get information of the block
            // TODO broadcast block breaking event
            // TODO broadcast block broken event if the first event is not cancelled
            println("Start digging $position $chunkPos $chunk $face")
        }

        GameMode.SURVIVAL -> TODO()
        GameMode.ADVENTURE -> TODO()
        GameMode.SPECTATOR -> TODO()
    }
}

//data class BlockBreakingEvent()