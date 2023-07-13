package io.github.daylightnebula.entities.packets

import io.github.daylightnebula.entities.EntityAnimation
import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode
import io.github.daylightnebula.noEncode

class JavaEntityAnimationPacket(
    var entityID: Int = 0,
    var animation: EntityAnimation = EntityAnimation.SWING_ARM
): JavaPacket {
    override val id: Int = 0x04
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeUByte(animation.ordinal.toUByte())
    }
}