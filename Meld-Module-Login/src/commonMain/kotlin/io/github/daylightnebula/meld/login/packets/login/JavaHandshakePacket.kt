package io.github.daylightnebula.meld.login.packets.login

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.utils.NotImplementedException

class JavaHandshakePacket(
    var version: Int = 0,
    var address: String = "",
    var port: UShort = 0u,
    var nextState: Int = 100
) : JavaPacket {

    companion object: JavaPacket.Creator<JavaHandshakePacket> {
        override val INCOMING_ID = 0x00
        override val STATE = JavaConnectionState.HANDSHAKE
        override fun create() = JavaHandshakePacket()
    }

    override val OUTGOING_ID: Int = INCOMING_ID

    override fun decode(reader: AbstractReader) {
        version = reader.readVarInt()
        address = reader.readVarString()
        port = reader.readUShort()
        nextState = reader.readVarInt()
    }

    override fun encode(writer: ByteWriter) {
        throw NotImplementedException("Receive Only")
    }
}