package io.github.daylightnebula.meld.server.inventories.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode

class JavaSetSelectedSlotPacket(
    var slot: Int = 0
): JavaPacket {
    override val id: Int = 0x4D // id when going to clients, 0x28 when being received
    override fun encode(writer: ByteWriter) {
        writer.writeShort(slot.toShort())
    }
    override fun decode(reader: AbstractReader) {
        slot = reader.readShort().toInt()
    }
}