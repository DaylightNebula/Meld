package io.github.daylightnebula.meld.inventories.inventories

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.inventories.EquipmentSlot
import io.github.daylightnebula.meld.inventories.ItemContainer
import io.github.daylightnebula.meld.inventories.packets.JavaSetEquipmentPacket
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection

interface EquippedInventory: Inventory {
    val entity: Entity

    // when the inventory changes, call an event and update equipment slot
    override fun onInventoryChange(changedSlot: Int, changedItemContainer: ItemContainer?, filled: Boolean) {
        // call event
        EventBus.callEvent(EquippedInventoryChangeEvent(entity, this, changedSlot, changedItemContainer, filled))

        // if an equipment slot can be found for the slot index, broadcast change
        getEquipmentSlotForIndex(changedSlot)?.let { broadcastSetItem(it) }
    }

    // functions for getting index to and from equipment slot
    fun getIndexForEquipmentSlot(slot: EquipmentSlot) = slot.ordinal
    fun getEquipmentSlotForIndex(index: Int): EquipmentSlot? = EquipmentSlot.values()[index]

    // send equipment slot packet
    fun broadcastSetItem(slot: EquipmentSlot) {
        // get item and build packets
        val item = getItem(getIndexForEquipmentSlot(slot))
        val javaPacket = JavaSetEquipmentPacket(entity.id, slot, item)

        // broadcast packets
        entity.getWatchers().forEach { connection ->
            when (connection) {
                is JavaConnection -> connection.sendPacket(javaPacket)
                is BedrockConnection -> NeedsBedrock()
            }
        }
    }
}



data class EquippedInventoryChangeEvent(
    val player: Entity,
    val inventory: EquippedInventory,
    val changedSlot: Int,
    val changedItemContainer: ItemContainer?,
    val fill: Boolean
): Event