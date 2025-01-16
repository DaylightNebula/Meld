package io.github.daylightnebula.meld.entities.packets

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaSpawnExpOrbPacket(
    var entityID: Int = 0,
    var position: Float3 = Float3(),
    var count: Short = 0
): JavaPacket {
    override val OUTGOING_ID: Int = 0x02
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeDouble(position.x.toDouble())
        writer.writeDouble(position.y.toDouble())
        writer.writeDouble(position.z.toDouble())
        writer.writeShort(count)
    }
}