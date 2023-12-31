package io.github.daylightnebula.meld.inventories.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode

class JavaCloseInventoryPacket(
    var inventoryID: UByte = 0u
): JavaPacket {
    override val id: Int = 0x11

    override fun encode(writer: ByteWriter) {
        writer.writeUByte(inventoryID)
    }

    override fun decode(reader: AbstractReader) {
        inventoryID = reader.readUByte()
    }
}