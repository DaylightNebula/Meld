package io.github.daylightnebula.meld.player.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaDifficultyPacket(
    var difficulty: UByte = 2u,
    var locked: Boolean = true
): JavaPacket {
    companion object: JavaPacket.Creator<JavaDifficultyPacket> {
        override val INCOMING_ID: Int = 0x0B
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaDifficultyPacket()
    }

    override val OUTGOING_ID: Int = 0x0B
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeUByte(difficulty)
        writer.writeBoolean(locked)
    }
}