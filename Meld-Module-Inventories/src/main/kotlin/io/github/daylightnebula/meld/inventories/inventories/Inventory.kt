package io.github.daylightnebula.meld.inventories.inventories

import io.github.daylightnebula.meld.server.utils.ItemContainer

interface Inventory {
    val slots: Array<ItemContainer?>

    fun onInventoryChange(changedSlot: Int, changedItemContainer: ItemContainer?, filled: Boolean)

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