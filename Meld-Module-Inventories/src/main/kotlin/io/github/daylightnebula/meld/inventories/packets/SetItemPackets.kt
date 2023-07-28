package io.github.daylightnebula.meld.inventories.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.utils.ItemContainer

class JavaSetItemPacket(
    var windowID: Byte = 0,
    var stateID: Int = 0,
    var slotIndex: Short = 0,
    var item: ItemContainer? = null
): JavaPacket {
    override val id: Int = 0x14
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeByte(windowID)
        writer.writeVarInt(stateID)
        writer.writeShort(slotIndex)
        writer.writeItem(item)
    }
}

class JavaSetInventoryContentPacket(
    var windowID: UByte = 0u,
    var stateID: Int = 0,
    var items: Array<ItemContainer?> = arrayOf(),
    var carriedItem: ItemContainer? = null
): JavaPacket {
    override val id: Int = 0x12
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeUByte(windowID)
        writer.writeVarInt(stateID)
        writer.writeVarInt(items.size)
        for (item in items) writer.writeItem(item)
        writer.writeItem(carriedItem)
    }
}