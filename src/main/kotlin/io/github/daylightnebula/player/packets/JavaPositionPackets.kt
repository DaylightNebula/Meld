package io.github.daylightnebula.player.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode
import io.github.daylightnebula.noEncode
import io.github.daylightnebula.player.TeleportCounter

class JavaReceivePlayerPositionPacket(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x14
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        x = reader.readDouble()
        y = reader.readDouble()
        z = reader.readDouble()
        onGround = reader.readBoolean()
    }
}

class JavaSetPlayerPositionPacket(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
    var yaw: Float = 0f,
    var pitch: Float = 0f,
    var flags: Byte = 0x00,
    var teleportID: Int = TeleportCounter.nextID()
): JavaPacket {
    override val id: Int = 0x3C
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeDouble(x)
        writer.writeDouble(y)
        writer.writeDouble(z)
        writer.writeFloat(yaw)
        writer.writeFloat(pitch)
        writer.writeByte(flags)
        writer.writeVarInt(teleportID)
    }
}