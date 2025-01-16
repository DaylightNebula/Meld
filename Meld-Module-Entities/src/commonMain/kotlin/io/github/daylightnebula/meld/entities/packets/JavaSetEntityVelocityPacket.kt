package io.github.daylightnebula.meld.entities.packets

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.extensions.toVelocityStep
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

data class JavaSetEntityVelocityPacket(
    var entityID: Int = 0,
    var velocity: Float3 = Float3()
): JavaPacket {
    override val id: Int = 0x5F
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeShort(velocity.x.toVelocityStep())
        writer.writeShort(velocity.y.toVelocityStep())
        writer.writeShort(velocity.z.toVelocityStep())
    }
}