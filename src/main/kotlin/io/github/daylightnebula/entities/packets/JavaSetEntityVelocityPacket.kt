package io.github.daylightnebula.entities.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode
import io.github.daylightnebula.noEncode
import org.cloudburstmc.math.vector.Vector3f

data class JavaSetEntityVelocityPacket(
    var entityID: Int = 0,
    var velocity: Vector3f = Vector3f.ZERO
): JavaPacket {
    override val id: Int = 0x54
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeShort(velocity.x.toVelocityStep())
        writer.writeShort(velocity.y.toVelocityStep())
        writer.writeShort(velocity.z.toVelocityStep())
    }
}