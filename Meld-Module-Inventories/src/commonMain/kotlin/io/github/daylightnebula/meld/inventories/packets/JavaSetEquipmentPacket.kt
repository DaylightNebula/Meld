package io.github.daylightnebula.meld.inventories.packets

import io.github.daylightnebula.meld.server.modules.inventories.EquipmentSlot
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.utils.ItemContainer

class JavaSetEquipmentPacket(
    var entityID: Int = 0,
    var slot: EquipmentSlot = EquipmentSlot.MAIN_HAND,
    var item: ItemContainer? = null
): JavaPacket {
    companion object: JavaPacket.Creator<JavaSetEquipmentPacket> {
        override val INCOMING_ID = 0x49
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaSetEquipmentPacket()
    }

    override val OUTGOING_ID: Int = 0x59
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeByte(slot.ordinal.toByte())
        writer.writeItem(item)
    }
}