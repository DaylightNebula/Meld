package io.github.daylightnebula.inventories

import org.jglrxavpok.hephaistos.nbt.NBTCompound

interface Inventory {
    val slots: Array<Item?>

    fun onInventoryChange(changedSlot: Int, changedItem: Item?)

    fun getItem(index: Int): Item? {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.java.name} (${slots.indices})")
        return slots[index]
    }

    fun setItem(index: Int, item: Item?) {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.java.name} (${slots.indices})")
        slots[index] = item
    }

    fun clear() {
        slots.indices.forEach { slots[it] = null }
    }
}

data class Item(
    val id: Int,
    val count: Byte,
    val nbt: NBTCompound?
)