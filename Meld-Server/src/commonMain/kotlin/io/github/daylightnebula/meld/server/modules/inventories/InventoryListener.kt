package io.github.daylightnebula.meld.server.modules.inventories

import io.github.daylightnebula.meld.server.entities.Player
import io.github.daylightnebula.meld.server.entities.PlayerBlockAction
import io.github.daylightnebula.meld.server.events.*
import io.github.daylightnebula.meld.server.modules.player.PlayerBlockActionEvent
import io.github.daylightnebula.meld.server.modules.player.PlayerEntityInteractEvent
import io.github.daylightnebula.meld.server.utils.ItemContainer
import io.github.daylightnebula.meld.server.utils.Slot
import io.github.daylightnebula.meld.server.utils.inventory
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InventoryListener: EventListener {
    override val executors: List<EventExecutor<*, *>> = listOf(
        EventExecutor(PlayerBlockActionEvent, this::onPlayerDropItem),
        EventExecutor(PlayerBlockActionEvent, this::onBlockAction),
        EventExecutor(PlayerEntityInteractEvent, this::onInteract)
    )

    fun onPlayerDropItem(event: PlayerBlockActionEvent) {
        // make sure drop item event
        if (event.action != PlayerBlockAction.DROP_ITEM && event.action != PlayerBlockAction.DROP_ITEM_STACK) return
        val inventory = event.player.inventory
        val item = inventory.getItem(inventory.selectedSlot + 36)

        // call event
        val dropEvent = PlayerDropRequestEvent(event.player, item)
        EventBus.callEvent(dropEvent)

        // if cancelled, add item back and stop here
//        if (!dropEvent.allowRemove) inventory.setItem(inventory.selectedSlot + 36, dropEvent.item)
        // otherwise remove item
//        else inventory.setItem(inventory.selectedSlot + 36, null)
    }

    fun onBlockAction(event: PlayerBlockActionEvent) {
        val inventory = event.player.inventory
        inventory.getItem(inventory.selectedSlot + 36)
//            ?.handler?.onBlockAction(event.action, event.face, event.blockPosition)
    }

    fun onInteract(event: PlayerEntityInteractEvent) {
        val inventory = event.player.inventory
        inventory.getItem(inventory.selectedSlot + 36)
//            ?.handler?.onEntityInteract(event.type, event.entityID, event.sneaking, event.targetPosition)
    }
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerDropRequestEvent(val player: Player, val item: Slot, var allowRemove: Boolean = false): Event {
    companion object: Event.Data<PlayerDropRequestEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerDropRequestEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}