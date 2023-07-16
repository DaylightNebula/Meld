package io.github.daylightnebula.meld.server.inventories

import org.jglrxavpok.hephaistos.nbt.NBTCompound

interface Inventory {
    val slots: Array<Item?>

    fun onInventoryChange(changedSlot: Int, changedItem: Item?, filled: Boolean)

    fun getItem(index: Int): Item? {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.java.name} (${slots.indices})")
        return slots[index]
    }

    fun setItem(index: Int, item: Item?) {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.java.name} (${slots.indices})")
        onInventoryChange(index, item, false)
        slots[index] = item
    }

    fun clear() {
        slots.indices.forEach { slots[it] = null }
        onInventoryChange(0, null, true)
    }

    fun setAll(item: Item) {
        slots.indices.forEach { slots[it] = item }
        onInventoryChange(0, item, true)
    }
}

data class Item(
    val id: Int,
    val count: Byte,
    val nbt: NBTCompound?
)