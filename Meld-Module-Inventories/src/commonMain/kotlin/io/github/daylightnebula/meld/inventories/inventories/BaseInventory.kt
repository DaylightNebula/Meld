package io.github.daylightnebula.meld.inventories.inventories

import io.github.daylightnebula.meld.inventories.PlayerDropRequestEvent
import io.github.daylightnebula.meld.player.PlayerEntityInteractEvent
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.utils.ItemContainer
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface BaseInventory {
    val slots: Array<ItemContainer?>

    fun onInventoryChange(changedSlot: Int, changedItemContainer: ItemContainer?, filled: Boolean) {
        EventBus.callEvent(InventoryChangeEvent(changedSlot, changedItemContainer, filled))
    }

    fun getItem(index: Int): ItemContainer? {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.simpleName} (${slots.indices})")
        return slots[index]
    }

    fun setItem(index: Int, itemContainer: ItemContainer?) {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.simpleName} (${slots.indices})")
        slots[index] = itemContainer
        onInventoryChange(index, itemContainer, false)
    }

    fun clear() {
        slots.indices.forEach { slots[it] = null }
        onInventoryChange(0, null, true)
    }

    fun setAll(itemContainer: ItemContainer) {
        slots.indices.forEach { slots[it] = itemContainer }
        onInventoryChange(0, itemContainer, true)
    }
}

@OptIn(ExperimentalUuidApi::class)
data class InventoryChangeEvent(val changedSlot: Int, val changedItemContainer: ItemContainer?, val filled: Boolean): Event {
    companion object: Event.Data<InventoryChangeEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(InventoryChangeEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}
