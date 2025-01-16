package io.github.daylightnebula.meld.entities.packets

import io.github.daylightnebula.meld.entities.EntityAnimation
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaEntityAnimationPacket(
    var entityID: Int = 0,
    var animation: EntityAnimation = EntityAnimation.SWING_ARM
): JavaPacket {
    companion object: JavaPacket.Creator<JavaEntityAnimationPacket> {
        override val INCOMING_ID: Int = 0x03
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaEntityAnimationPacket()
    }

    override val OUTGOING_ID: Int = 0x03
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeUByte(animation.ordinal.toUByte())
    }
}