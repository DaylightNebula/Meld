package io.github.daylightnebula.meld.inventories.handlers

import io.github.daylightnebula.meld.player.PlayerBlockAction
import io.github.daylightnebula.meld.player.PlayerInteractType
import io.github.daylightnebula.meld.server.utils.BlockFace
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i

interface ItemHandler {
    val id: Int
    val customModelID: Int?
    fun onBlockAction(action: PlayerBlockAction, face: BlockFace, position: Vector3i)
    fun onEntityInteract(type: PlayerInteractType, entityID: Int, sneaking: Boolean, targetPosition: Vector3f?)
}