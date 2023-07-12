package io.github.daylightnebula.inventories

import io.github.daylightnebula.events.Event
import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.player.Player

class PlayerInventory(val player: Player): Inventory {
    var selectedSlot: Int = 0
    override val slots: Array<Item?> = arrayOfNulls(46)
    override fun onInventoryChange(changedSlot: Int, changedItem: Item?, fill: Boolean) {
        EventBus.callEvent(PlayerInventoryChangeEvent(player, this, changedSlot, changedItem, fill))
    }
}

data class PlayerInventoryChangeEvent(
    val player: Player,
    val inventory: PlayerInventory,
    val changedSlot: Int,
    val changedItem: Item?,
    val fill: Boolean
): Event