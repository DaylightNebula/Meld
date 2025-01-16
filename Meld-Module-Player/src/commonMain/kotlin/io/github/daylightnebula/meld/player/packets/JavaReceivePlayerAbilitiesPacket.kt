package io.github.daylightnebula.meld.player.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode

class JavaReceivePlayerAbilitiesPacket(
    var flags: Byte = 0
): JavaPacket {
    companion object: JavaPacket.Creator<JavaReceivePlayerAbilitiesPacket> {
        override val INCOMING_ID: Int = 0x3A
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaReceivePlayerAbilitiesPacket()
    }

    override val OUTGOING_ID: Int = 0x3A
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        flags = reader.readByte()
    }
}