package io.github.daylightnebula.meld.entities.packets

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.extensions.toAngleByte
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

data class JavaUpdateEntityPositionPacket(
    var entityID: Int = 0,
    var delta: Float3 = Float3(),
    var onGround: Boolean = false
): JavaPacket {
    override val OUTGOING_ID: Int = 0x2F
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
    var delta: Float3 = Float3(),
    var rotation: Float2 = Float2(),
    var onGround: Boolean = false
): JavaPacket {
    override val OUTGOING_ID: Int = 0x30
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
    var rotation: Float2 = Float2(),
    var onGround: Boolean = false
): JavaPacket {
    override val OUTGOING_ID: Int = 0x32
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeByte(rotation.x.toAngleByte())
        writer.writeByte(rotation.y.toAngleByte())
        writer.writeBoolean(onGround)
    }
}

data class JavaUpdateHeadYawPacket(
    var entityID: Int = 0,
    var yaw: Float = 0f
): JavaPacket {
    override val OUTGOING_ID: Int = 0x46
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeByte(yaw.toAngleByte())
    }
}