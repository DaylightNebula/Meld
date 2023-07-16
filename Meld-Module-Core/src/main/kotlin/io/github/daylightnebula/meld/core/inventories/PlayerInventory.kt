package io.github.daylightnebula.meld.core.inventories

import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.core.player.Player

class PlayerInventory(val player: Player): Inventory {
    var selectedSlot: Int = 0
    override val slots: Array<Item?> = arrayOfNulls(46)
    override fun onInventoryChange(changedSlot: Int, changedItem: Item?, filled: Boolean) {
        EventBus.callEvent(PlayerInventoryChangeEvent(player, this, changedSlot, changedItem, filled))
    }
}

data class PlayerInventoryChangeEvent(
    val player: Player,
    val inventory: PlayerInventory,
    val changedSlot: Int,
    val changedItem: Item?,
    val fill: Boolean
): Event