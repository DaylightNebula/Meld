package io.github.daylightnebula.meld.entities.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaRemoveEntitiesPacket(
    val entityIDs: Collection<Int> = listOf<Int>()
): JavaPacket {
    override val id: Int = 0x40
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityIDs.size)
        for (entityID in entityIDs) writer.writeVarInt(entityID)
    }
}