package io.github.daylightnebula.meld.login.packets

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

    companion object {
        val ID = 0x00
        val TYPE = JavaConnectionState.HANDSHAKE
    }

    override val id: Int = ID

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