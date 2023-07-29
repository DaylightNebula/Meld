package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.inventories.handlers.handler
import io.github.daylightnebula.meld.inventories.utils.inventory
import io.github.daylightnebula.meld.player.*
import io.github.daylightnebula.meld.server.events.*
import io.github.daylightnebula.meld.server.utils.ItemContainer

class InventoryListener: EventListener {
    @EventHandler
    fun onPlayerDropItem(event: PlayerBlockActionEvent) {
        // make sure drop item event
        if (event.action != PlayerBlockAction.DROP_ITEM && event.action != PlayerBlockAction.DROP_ITEM_STACK) return
        val inventory = event.player.inventory
        val item = inventory.getItem(inventory.selectedSlot + 36)

        // call event
        val dropEvent = PlayerDropRequestEvent(event.player, item)
        EventBus.callEvent(dropEvent)

        // if cancelled, add item back and stop here
        if (!dropEvent.allowRemove) inventory.setItem(inventory.selectedSlot + 36, dropEvent.item)
        // otherwise remove item
        else inventory.setItem(inventory.selectedSlot + 36, null)
    }

    @EventHandler
    fun onBlockAction(event: PlayerBlockActionEvent) {
        val inventory = event.player.inventory
        inventory.getItem(inventory.selectedSlot + 36)
            ?.handler?.onBlockAction(event.action, event.face, event.blockPosition)
    }

    @EventHandler
    fun onInteract(event: PlayerEntityInteractEvent) {
        val inventory = event.player.inventory
        inventory.getItem(inventory.selectedSlot + 36)
            ?.handler?.onEntityInteract(event.type, event.entityID, event.sneaking, event.targetPosition)
    }
}

data class PlayerDropRequestEvent(val player: Player, val item: ItemContainer?, var allowRemove: Boolean = false): Event