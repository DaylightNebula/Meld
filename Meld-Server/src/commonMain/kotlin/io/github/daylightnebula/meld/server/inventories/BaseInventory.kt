package io.github.daylightnebula.meld.server.inventories

import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.utils.ItemContainer
import io.github.daylightnebula.meld.server.utils.Slot
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface BaseInventory {
    val slots: MutableList<Slot>

    fun onInventoryChange(changedSlot: Int, changedItemContainer: Slot, filled: Boolean) {
        EventBus.callEvent(InventoryChangeEvent(changedSlot, changedItemContainer, filled))
    }

    fun getItem(index: Int): Slot {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.simpleName} (${slots.indices})")
        return slots[index]
    }

    fun setItem(index: Int, itemContainer: Slot) {
        if (!slots.indices.contains(index))
            throw IndexOutOfBoundsException("Index is not inside bounds of ${this::class.simpleName} (${slots.indices})")
        slots[index] = itemContainer
        onInventoryChange(index, itemContainer, false)
    }

    fun clear() {
        slots.indices.forEach { slots[it] = Slot.Empty() }
        onInventoryChange(0, Slot.Empty(), true)
    }

    fun setAll(itemContainer: Slot) {
        slots.indices.forEach { slots[it] = itemContainer }
        onInventoryChange(0, itemContainer, true)
    }
}

@OptIn(ExperimentalUuidApi::class)
data class InventoryChangeEvent(val changedSlot: Int, val changedItemContainer: Slot, val filled: Boolean): Event {
    companion object: Event.Data<InventoryChangeEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(InventoryChangeEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}
