package io.github.daylightnebula.meld.player.packets.join

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class JavaPluginMessagePacket(
    var channel: String = "",
    var data: ByteArray = byteArrayOf()
): JavaPacket {
    companion object: JavaPacket.Creator<JavaPluginMessagePacket> {
        override val INCOMING_ID: Int = 0x19
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaPluginMessagePacket()
    }

    override val OUTGOING_ID: Int = 0x19 // id used to send to client
    override fun encode(writer: ByteWriter) {
        writer.writeString(channel)
        writer.writeByteArray(data)
    }
    override fun decode(reader: AbstractReader) {
        channel = reader.readVarString()
        data = reader.readBytes(reader.remaining())
    }
}