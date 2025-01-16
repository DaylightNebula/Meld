package io.github.daylightnebula.meld.entities.packets

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.extensions.toVelocityStep
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

data class JavaSetEntityVelocityPacket(
    var entityID: Int = 0,
    var velocity: Float3 = Float3()
): JavaPacket {
    companion object: JavaPacket.Creator<JavaSetEntityVelocityPacket> {
        override val INCOMING_ID: Int = 0x5F
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaSetEntityVelocityPacket()
    }

    override val OUTGOING_ID: Int = 0x5F
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeShort(velocity.x.toVelocityStep())
        writer.writeShort(velocity.y.toVelocityStep())
        writer.writeShort(velocity.z.toVelocityStep())
    }
}