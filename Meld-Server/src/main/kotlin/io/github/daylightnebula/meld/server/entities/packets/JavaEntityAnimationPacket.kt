package io.github.daylightnebula.meld.server.entities.packets

import io.github.daylightnebula.meld.server.entities.EntityAnimation
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.noEncode

class JavaEntityAnimationPacket(
    var entityID: Int = 0,
    var animation: EntityAnimation = EntityAnimation.SWING_ARM
): JavaPacket {
    override val id: Int = 0x04
    override fun decode(reader: AbstractReader) = io.github.daylightnebula.meld.server.noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeUByte(animation.ordinal.toUByte())
    }
}