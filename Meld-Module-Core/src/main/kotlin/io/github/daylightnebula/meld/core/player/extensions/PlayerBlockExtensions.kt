package io.github.daylightnebula.meld.core.player.extensions

import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.meld.core.player.Player
import io.github.daylightnebula.meld.core.player.PlayerBlockAction
import io.github.daylightnebula.meld.core.worlds.World
import io.github.daylightnebula.meld.core.worlds.chunks.toChunkPosition
import org.cloudburstmc.math.vector.Vector3i

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

            // call set block
            chunk.setBlock(this, position, 0)
        }

        GameMode.SURVIVAL -> TODO()
        GameMode.ADVENTURE -> TODO()
        GameMode.SPECTATOR -> TODO()
    }
}