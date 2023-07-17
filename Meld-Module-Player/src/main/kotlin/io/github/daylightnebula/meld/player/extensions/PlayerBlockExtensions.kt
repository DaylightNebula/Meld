package io.github.daylightnebula.meld.player.extensions

/*
import com.github.steveice10.mc.protocol.data.game.entity.player.GameMode
import io.github.daylightnebula.meld.world.World
import io.github.daylightnebula.meld.world.chunks.toChunkPosition
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.player.PlayerBlockAction
import org.cloudburstmc.math.vector.Vector3i

fun Player.handleBlockAction(action: PlayerBlockAction, position: Vector3i, face: Byte) {
    when(action) {
        PlayerBlockAction.START_DIGGING -> handleStartDiggingAction(position, face)
        PlayerBlockAction.CANCELLED_DIGGING -> {}
        PlayerBlockAction.FINISHED_DIGGING -> {}
        PlayerBlockAction.DROP_ITEM_STACK -> {}
        PlayerBlockAction.DROP_ITEM -> {}
        PlayerBlockAction.SHOOT_ARROW_FINISH_EATING -> {}
        PlayerBlockAction.SWAP_ITEM_IN_HAND -> {}
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

        GameMode.SURVIVAL -> {}
        GameMode.ADVENTURE -> {}
        GameMode.SPECTATOR -> {}
    }
}*/
