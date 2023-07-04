package io.github.daylightnebula.player.packets.login

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode

class JavaFeatureFlagsPacket(var features: Array<String> = arrayOf("minecraft:vanilla")): JavaPacket {
    override val id: Int = 0x6B
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(features.size)
        features.forEach { writer.writeString(it) }
    }
}

class JavaAbilitiesPacket(
    var moveSpeed: Float = 0.1f,
    var flySpeed: Float = 0.05f,
    var flags: Byte = 0x04
): JavaPacket {
    override val id: Int = 0x34
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeByte(flags)
        writer.writeFloat(flySpeed)
        writer.writeFloat(moveSpeed)
    }
}