package io.github.daylightnebula.login.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaPacket
import jdk.jshell.spi.ExecutionControl.NotImplementedException

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