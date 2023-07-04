package io.github.daylightnebula.player.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noEncode

class JavaReceivePlayerAbilitiesPacket(
    var flags: Byte = 0
): JavaPacket {
    override val id: Int = 0x1C
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        flags = reader.readByte()
    }
}