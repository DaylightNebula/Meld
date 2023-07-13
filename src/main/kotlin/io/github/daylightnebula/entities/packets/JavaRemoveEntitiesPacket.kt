package io.github.daylightnebula.entities.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode

class JavaRemoveEntitiesPacket(
    val entityIDs: MutableList<Int> = mutableListOf<Int>()
): JavaPacket {
    override val id: Int = 0x3E
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityIDs.size)
        for (entityID in entityIDs) writer.writeVarInt(entityID)
    }
}