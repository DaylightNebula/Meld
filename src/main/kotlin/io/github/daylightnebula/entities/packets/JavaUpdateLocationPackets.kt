package io.github.daylightnebula.entities.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3i

data class JavaUpdateEntityPositionPacket(
    var entityID: Int = 0,
    var delta: Vector3i = Vector3i.ZERO,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x2B
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeShort((delta.x * 4096).toShort())
        writer.writeShort((delta.y * 4096).toShort())
        writer.writeShort((delta.z * 4096).toShort())
        writer.writeBoolean(onGround)
    }
}

data class JavaUpdateEntityPositionAndRotationPacket(
    var entityID: Int = 0,
    var delta: Vector3i = Vector3i.ZERO,
    var rotation: Vector2f = Vector2f.ZERO,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x2C
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeShort((delta.x * 4096).toShort())
        writer.writeShort((delta.y * 4096).toShort())
        writer.writeShort((delta.z * 4096).toShort())
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