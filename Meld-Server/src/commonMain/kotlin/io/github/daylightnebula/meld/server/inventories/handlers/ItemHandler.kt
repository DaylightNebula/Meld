package io.github.daylightnebula.meld.server.inventories.handlers

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.entities.PlayerBlockAction
import io.github.daylightnebula.meld.server.entities.PlayerInteractType
import io.github.daylightnebula.meld.server.utils.BlockFace

interface ItemHandler {
    val id: Int
    val customModelID: Int?
    fun onBlockAction(action: PlayerBlockAction, face: BlockFace, position: Float3)
    fun onEntityInteract(type: PlayerInteractType, entityID: Int, sneaking: Boolean, targetPosition: Float3?)
}