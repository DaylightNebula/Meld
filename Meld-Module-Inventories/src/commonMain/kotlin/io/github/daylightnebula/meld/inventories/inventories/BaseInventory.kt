package io.github.daylightnebula.meld.inventories.inventories

import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.utils.ItemContainer

interface BaseInventory {
    val slots: Array<ItemContainer?>

    fun onInventoryChange(changedSlot: Int, changedItemContainer: ItemContainer?, filled: Boolean) {
        EventBus.callEvent(InventoryChangeEvent(changedSlot, changedItemContainer, filled))
    }

    fun getItem(index: Int): ItemContainer? {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.java.name} (${slots.indices})")
        return slots[index]
    }

    fun setItem(index: Int, itemContainer: ItemContainer?) {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.java.name} (${slots.indices})")
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

data class InventoryChangeEvent(val changedSlot: Int, val changedItemContainer: ItemContainer?, val filled: Boolean): Event