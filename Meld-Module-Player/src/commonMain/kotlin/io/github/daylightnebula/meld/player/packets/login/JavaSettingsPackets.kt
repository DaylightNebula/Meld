package io.github.daylightnebula.meld.player.packets.login

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaAbilitiesPacket(
    var moveSpeed: Float = 0.1f,
    var flySpeed: Float = 0.05f,
    var flags: Byte = 0x04
): JavaPacket {
    companion object: JavaPacket.Creator<JavaAbilitiesPacket> {
        override val INCOMING_ID = 0x3A
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaAbilitiesPacket()
    }

    override val OUTGOING_ID: Int = 0x3A
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeByte(flags)
        writer.writeFloat(flySpeed)
        writer.writeFloat(moveSpeed)
    }
}