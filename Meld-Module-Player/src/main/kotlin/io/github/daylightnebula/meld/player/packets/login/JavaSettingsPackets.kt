package io.github.daylightnebula.meld.player.packets.login

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaAbilitiesPacket(
    var moveSpeed: Float = 0.1f,
    var flySpeed: Float = 0.05f,
    var flags: Byte = 0x04
): JavaPacket {
    override val id: Int = 0x36
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeByte(flags)
        writer.writeFloat(flySpeed)
        writer.writeFloat(moveSpeed)
    }
}