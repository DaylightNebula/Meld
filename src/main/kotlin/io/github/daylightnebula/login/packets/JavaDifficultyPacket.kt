package io.github.daylightnebula.login.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode

class JavaDifficultyPacket(
    var difficulty: UByte = 2u,
    var locked: Boolean = true
): JavaPacket {
    override val id: Int = 0x0C
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeUByte(difficulty)
        writer.writeBoolean(locked)
    }
}