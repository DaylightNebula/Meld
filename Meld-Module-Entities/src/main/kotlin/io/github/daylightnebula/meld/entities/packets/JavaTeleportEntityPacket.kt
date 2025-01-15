package io.github.daylightnebula.meld.entities.packets

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.extensions.toAngleByte
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaTeleportEntityPacket(
    var entityID: Int = 0,
    var position: Float3 = Float3(),
    var rotation: Float2 = Float2(),
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x6D
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeDouble(position.x.toDouble())
        writer.writeDouble(position.y.toDouble())
        writer.writeDouble(position.z.toDouble())
        writer.writeByte(rotation.x.toAngleByte())
        writer.writeByte(rotation.y.toAngleByte())
        writer.writeBoolean(onGround)
    }
}