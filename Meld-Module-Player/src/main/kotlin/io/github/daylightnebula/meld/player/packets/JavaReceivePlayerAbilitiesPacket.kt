package io.github.daylightnebula.meld.player.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode

class JavaReceivePlayerAbilitiesPacket(
    var flags: Byte = 0
): JavaPacket {
    override val id: Int = 0x20
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        flags = reader.readByte()
    }
}