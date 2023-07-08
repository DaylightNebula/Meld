package io.github.daylightnebula.worlds.chunks.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode

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