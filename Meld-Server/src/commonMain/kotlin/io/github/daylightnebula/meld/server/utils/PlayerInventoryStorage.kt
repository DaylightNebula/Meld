package io.github.daylightnebula.meld.server.utils

import io.github.daylightnebula.meld.server.entities.Player
import io.github.daylightnebula.meld.server.inventories.PlayerInventory

// storage for all active player inventories
val playerInventories = hashMapOf<Player, PlayerInventory>()

// extend a variable into player for accessing and setting player inventories
val Player.inventory: PlayerInventory
    get() {
        var inventory = playerInventories[this]
        if (inventory == null) {
            inventory = PlayerInventory(this)
            playerInventories[this] = inventory
        }
        return inventory
    }