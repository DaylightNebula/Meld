package io.github.daylightnebula.meld.inventories.inventories

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.inventories.EquipmentSlot
import io.github.daylightnebula.meld.inventories.packets.JavaSetEquipmentPacket
import io.github.daylightnebula.meld.inventories.packets.JavaSetInventoryContentPacket
import io.github.daylightnebula.meld.inventories.packets.JavaSetItemPacket
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.utils.ItemContainer

interface EntityInventory: BaseInventory {
    val entity: Entity
    val id: UByte

    // when the inventory changes, call an event and update equipment slot
    override fun onInventoryChange(changedSlot: Int, changedItemContainer: ItemContainer?, filled: Boolean) {
        super.onInventoryChange(changedSlot, changedItemContainer, filled)

        // if an equipment slot can be found for the slot index, broadcast change
        getEquipmentSlotForIndex(changedSlot)?.let { broadcastEquipmentChange(it) }

        // broadcast item changes
        if (entity is Player) {
            // get packets
            val (javaPacket, bedrockPacket) = when (filled) {
                true -> JavaSetInventoryContentPacket(id, 0, slots, null) to null
                false -> JavaSetItemPacket(id.toByte(), 0, changedSlot.toShort(), changedItemContainer) to null
            }

            // send to player
            val player = (entity as Player).connection
            when (player) {
                is JavaConnection -> player.sendPacket(javaPacket)
                is BedrockConnection -> NeedsBedrock()
            }
        }
    }

    // functions for getting index to and from equipment slot
    fun getIndexForEquipmentSlot(slot: EquipmentSlot) = slot.ordinal
    fun getEquipmentSlotForIndex(index: Int): EquipmentSlot? = EquipmentSlot.values()[index]

    // send equipment slot packet
    fun broadcastEquipmentChange(slot: EquipmentSlot) {
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