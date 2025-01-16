package io.github.daylightnebula.meld.login.packets.config

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class JavaConfigMessagePacket(
    var channel: String = "",
    var data: ByteArray = byteArrayOf()
): JavaPacket {
    override val id: Int = 0x01
    override fun encode(writer: ByteWriter) {
        writer.writeString(channel)
        writer.writeByteArray(data)
    }
    override fun decode(reader: AbstractReader) {
        channel = reader.readVarString()
        data = reader.readArray(reader.remaining())
    }
}