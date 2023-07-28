package io.github.daylightnebula.meld.inventories.packets

import io.github.daylightnebula.meld.inventories.EquipmentSlot
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.utils.ItemContainer

class JavaSetEquipmentPacket(
    var entityID: Int = 0,
    var slot: EquipmentSlot,
    var item: ItemContainer?
): JavaPacket {
    override val id: Int = 0x55
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeByte(slot.ordinal.toByte())
        writer.writeItem(item)
    }
}