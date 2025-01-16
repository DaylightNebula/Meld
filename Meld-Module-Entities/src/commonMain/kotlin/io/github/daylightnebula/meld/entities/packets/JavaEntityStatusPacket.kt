package io.github.daylightnebula.meld.entities.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class JavaEntityStatusPacket(
    var entityID: Int = 0,
    var status: Byte = 0
) : JavaPacket {
    companion object: JavaPacket.Creator<JavaEntityStatusPacket> {
        override val INCOMING_ID: Int = 0x1F
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaEntityStatusPacket()
    }

    override val OUTGOING_ID: Int = 0x1F
    override fun decode(reader: AbstractReader) = io.github.daylightnebula.meld.server.noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeInt(entityID)
        writer.writeByte(status)
    }
}