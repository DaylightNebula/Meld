package io.github.daylightnebula.meld.entities.packets

import io.github.daylightnebula.meld.entities.metadata.EntityMetadata
import io.github.daylightnebula.meld.entities.metadata.entityMetadata
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaEntityMetadataPacket(
    var entityID: Int = 0,
    var metadata: EntityMetadata = entityMetadata()
): JavaPacket {
    override val id: Int = 0x56
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        metadata.writeJava(writer)
        writer.writeByte((0xFF).toByte()) // terminate metadata
    }
}