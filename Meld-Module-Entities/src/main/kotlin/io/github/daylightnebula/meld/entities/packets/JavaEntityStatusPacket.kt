package io.github.daylightnebula.meld.entities.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class JavaEntityStatusPacket(
    var entityID: Int = 0,
    var status: Byte = 0
) : JavaPacket {
    override val id: Int = 0x1D
    override fun decode(reader: AbstractReader) = io.github.daylightnebula.meld.server.noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeInt(entityID)
        writer.writeByte(status)
    }
}