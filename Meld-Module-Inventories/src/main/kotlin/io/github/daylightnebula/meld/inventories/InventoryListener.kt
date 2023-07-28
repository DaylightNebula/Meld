package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.player.*
import io.github.daylightnebula.meld.server.events.CancellableEvent
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.events.EventHandler
import io.github.daylightnebula.meld.server.events.EventListener
import io.github.daylightnebula.meld.server.utils.ItemContainer

class InventoryListener: EventListener {
    @EventHandler
    fun onPlayerDropItem(event: PlayerBlockActionEvent) {
        // make sure drop item event
        if (event.action != PlayerBlockAction.DROP_ITEM && event.action != PlayerBlockAction.DROP_ITEM_STACK) return
        val inventory = event.player.inventory
        val item = inventory.getItem(inventory.selectedSlot + 36)

        // call event
        val dropEvent = PlayerDropItemEvent(event.player, item)
        EventBus.callEvent(dropEvent)

        // if cancelled, add item back and stop here
        if (dropEvent.cancelled) {
            inventory.setItem(inventory.selectedSlot + 36, dropEvent.item)
            return
        }

        // if drop event is marked spawn drop entity, do so
        if (dropEvent.spawnDropEntity) {
            ItemEntity(
                item = item,
                position = event.player.position.clone()
            )
        }
    }
}

data class PlayerDropItemEvent(val player: Player, val item: ItemContainer?, val spawnDropEntity: Boolean = true, override var cancelled: Boolean = false): CancellableEvent