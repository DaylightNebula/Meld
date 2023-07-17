package io.github.daylightnebula.meld.entities.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
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