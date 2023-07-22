package io.github.daylightnebula.meld.world.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import org.cloudburstmc.math.vector.Vector2i

class JavaUnloadChunkPacket(
    var chunkPos: Vector2i = Vector2i.ZERO
): JavaPacket {
    override val id: Int = 0x1E
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeInt(chunkPos.x)
        writer.writeInt(chunkPos.y)
    }
}