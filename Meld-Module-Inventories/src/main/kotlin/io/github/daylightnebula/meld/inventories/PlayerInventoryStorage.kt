package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.inventories.inventories.PlayerInventory
import io.github.daylightnebula.meld.player.Player

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