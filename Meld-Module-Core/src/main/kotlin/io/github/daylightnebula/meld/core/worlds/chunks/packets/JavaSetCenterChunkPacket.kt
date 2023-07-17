package io.github.daylightnebula.meld.core.worlds.chunks.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaSetCenterChunkPacket(
    var chunkX: Int = 0,
    var chunkY: Int = 0
): JavaPacket {
    override val id: Int = 0x4E
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(chunkX)
        writer.writeVarInt(chunkY)
    }
}