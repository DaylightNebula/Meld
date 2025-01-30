package io.github.daylightnebula.meld.server.inventories

import io.github.daylightnebula.meld.server.entities.Entity
import io.github.daylightnebula.meld.server.entities.Player
import io.github.daylightnebula.meld.server.generated.JavaClientPlaySetSlot
import io.github.daylightnebula.meld.server.generated.JavaClientPlayWindowItems
import io.github.daylightnebula.meld.server.modules.inventories.EquipmentSlot
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.utils.Slot

interface EntityInventory: BaseInventory {
    val entity: Entity
    val id: Int

    // when the inventory changes, call an event and update equipment slot
    override fun onInventoryChange(changedSlot: Int, changedItemContainer: Slot, filled: Boolean) {
        super.onInventoryChange(changedSlot, changedItemContainer, filled)

        // if an equipment slot can be found for the slot index, broadcast change
        getEquipmentSlotForIndex(changedSlot)?.let { broadcastEquipmentChange(it) }

        // broadcast item changes
        if (entity is Player) {
            // get packets
            val javaPacket = when (filled) {
                true -> JavaClientPlayWindowItems(id, 0, slots, Slot.Empty())
                false -> JavaClientPlaySetSlot(id, 0, changedSlot.toShort(), changedItemContainer)
            }

            // send to player
            val player = (entity as Player).connection
            when (player) {
                is JavaConnection -> player.sendPacket(javaPacket)
            }
        }
    }

    // functions for getting index to and from equipment slot
    fun getIndexForEquipmentSlot(slot: EquipmentSlot) = slot.ordinal
    fun getEquipmentSlotForIndex(index: Int): EquipmentSlot? = EquipmentSlot.entries[index]

    // send equipment slot packet
    fun broadcastEquipmentChange(slot: EquipmentSlot) {
        // get item and build packets
        val item = getItem(getIndexForEquipmentSlot(slot))
        TODO()
//      TODO  val javaPacket = JavaClientPlayEntityEquipment(entity.id, slot, item)
//
//        // broadcast packets
//        entity.getWatchers().forEach { connection ->
//            when (connection) {
//                is JavaConnection -> connection.sendPacket(javaPacket)
////              BEDROCK  is BedrockConnection -> NeedsBedrock()
//            }
//        }
    }
}