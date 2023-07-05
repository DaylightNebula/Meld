package io.github.daylightnebula.player.packets.join

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noEncode

class JavaPluginMessagePacket(
    var channel: String = "",
    var data: ByteArray = byteArrayOf()
): JavaPacket {
    override val id: Int = 0x17 // id used to send to client
    override fun encode(writer: ByteWriter) {
        writer.writeString(channel)
        writer.writeByteArray(data)
    }
    override fun decode(reader: AbstractReader) {
        channel = reader.readVarString()
        data = reader.readArray(reader.remaining())
    }
}