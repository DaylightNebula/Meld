package io.github.daylightnebula.meld.login.packets.config

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class JavaConfigMessagePacket(
    var channel: String = "",
    var data: ByteArray = byteArrayOf()
): JavaPacket {
    companion object: JavaPacket.Creator<JavaConfigMessagePacket> {
        override val INCOMING_ID = 0x02
        override val STATE = JavaConnectionState.CONFIG
        override fun create() = JavaConfigMessagePacket()
    }

    override val OUTGOING_ID: Int = 0x01
    override fun encode(writer: ByteWriter) {
        writer.writeString(channel)
        writer.writeByteArray(data)
    }
    override fun decode(reader: AbstractReader) {
        channel = reader.readVarString()
        data = reader.readArray(reader.remaining())
    }
}