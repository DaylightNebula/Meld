package io.github.daylightnebula.meld.login.packets.config

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class JavaFinishConfigPacket(): JavaPacket {
    override val id: Int = 0x02
    override fun encode(writer: ByteWriter) {}
    override fun decode(reader: AbstractReader) {}
}