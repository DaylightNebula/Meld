package io.github.daylightnebula.meld.inventories.inventories

import io.github.daylightnebula.meld.inventories.EquipmentSlot
import io.github.daylightnebula.meld.inventories.ItemContainer
import io.github.daylightnebula.meld.inventories.packets.JavaSetEquipmentPacket
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection

class PlayerInventory(
    override val entity: Player,
    override val slots: Array<ItemContainer?> = arrayOfNulls(46)
): EquippedInventory {
    var selectedSlot: Int = 0 // offset 36
        set(value) {
            field = value

            // set equipment in main hand accordingly
            broadcastSetItem(EquipmentSlot.MAIN_HAND)
        }

    override fun getIndexForEquipmentSlot(slot: EquipmentSlot) = when (slot) {
        EquipmentSlot.MAIN_HAND -> 36 + selectedSlot
        EquipmentSlot.OFF_HAND -> 45
        EquipmentSlot.BOOTS -> 8
        EquipmentSlot.LEGGINGS -> 7
        EquipmentSlot.CHESTPLATE -> 6
        EquipmentSlot.HELMET -> 5
    }

    override fun getEquipmentSlotForIndex(index: Int): EquipmentSlot? = when (index) {
        in 36..44 -> if (index - 36 == selectedSlot) EquipmentSlot.MAIN_HAND else null
        45 -> EquipmentSlot.OFF_HAND
        5 -> EquipmentSlot.HELMET
        6 -> EquipmentSlot.CHESTPLATE
        7 -> EquipmentSlot.LEGGINGS
        8 -> EquipmentSlot.BOOTS
        else -> null
    }
}