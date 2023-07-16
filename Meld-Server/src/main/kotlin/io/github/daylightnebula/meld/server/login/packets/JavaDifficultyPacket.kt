package io.github.daylightnebula.meld.server.login.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaDifficultyPacket(
    var difficulty: UByte = 2u,
    var locked: Boolean = true
): JavaPacket {
    override val id: Int = 0x0C
    override fun decode(reader: AbstractReader) = io.github.daylightnebula.meld.server.noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeUByte(difficulty)
        writer.writeBoolean(locked)
    }
}