package io.github.daylightnebula.inventories.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noEncode

class JavaCloseInventoryPacket(
    var inventoryID: UByte = 0u
): JavaPacket {
    override val id: Int = 0x0C
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        inventoryID = reader.readUByte()
    }
}