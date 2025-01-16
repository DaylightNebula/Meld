package io.github.daylightnebula.meld.player.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode

class JavaPlayerClientTick: JavaPacket {
    companion object: JavaPacket.Creator<JavaPlayerClientTick> {
        override val INCOMING_ID: Int = 0x0B
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaPlayerClientTick()
    }

    override val OUTGOING_ID: Int = 0x0B
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {}
}