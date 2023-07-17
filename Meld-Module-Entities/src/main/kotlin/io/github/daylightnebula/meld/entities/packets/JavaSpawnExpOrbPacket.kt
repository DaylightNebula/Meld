package io.github.daylightnebula.meld.entities.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import org.cloudburstmc.math.vector.Vector3f

class JavaSpawnExpOrbPacket(
    var entityID: Int = 0,
    var position: Vector3f = Vector3f.from(0f, 0f, 0f),
    var count: Short = 0
): JavaPacket {
    override val id: Int = 0x02
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeDouble(position.x.toDouble())
        writer.writeDouble(position.y.toDouble())
        writer.writeDouble(position.z.toDouble())
        writer.writeShort(count)
    }
}