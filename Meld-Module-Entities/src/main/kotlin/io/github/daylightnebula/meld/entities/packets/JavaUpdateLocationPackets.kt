package io.github.daylightnebula.meld.entities.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f

data class JavaUpdateEntityPositionPacket(
    var entityID: Int = 0,
    var delta: Vector3f = Vector3f.ZERO,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x2B
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeShort(delta.x.toInt().toShort())
        writer.writeShort(delta.y.toInt().toShort())
        writer.writeShort(delta.z.toInt().toShort())
        writer.writeBoolean(onGround)
    }
}

data class JavaUpdateEntityPositionAndRotationPacket(
    var entityID: Int = 0,
    var delta: Vector3f = Vector3f.ZERO,
    var rotation: Vector2f = Vector2f.ZERO,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x2C
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeShort(delta.x.toInt().toShort())
        writer.writeShort(delta.y.toInt().toShort())
        writer.writeShort(delta.z.toInt().toShort())
        writer.writeByte(rotation.x.toAngleByte())
        writer.writeByte(rotation.y.toAngleByte())
        writer.writeBoolean(onGround)
    }
}

data class JavaUpdateEntityRotationPacket(
    var entityID: Int = 0,
    var rotation: Vector2f = Vector2f.ZERO,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x2D
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeByte(rotation.x.toAngleByte())
        writer.writeByte(rotation.y.toAngleByte())
        writer.writeBoolean(onGround)
    }
}