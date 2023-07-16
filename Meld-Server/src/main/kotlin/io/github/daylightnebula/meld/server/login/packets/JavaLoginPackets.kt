package io.github.daylightnebula.meld.server.login.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import jdk.jshell.spi.ExecutionControl.NotImplementedException
import java.util.*

class JavaInitiateLoginPacket(
    var username: String = "",
    var hasUUID: Boolean = false,
    var uuid: UUID? = null
): JavaPacket {
    companion object {
        val ID = 0x00
        val TYPE = JavaConnectionState.LOGIN
    }

    override val id: Int = ID

    override fun encode(writer: ByteWriter) {
        throw NotImplementedException("This packet can only be received!")
    }

    override fun decode(reader: AbstractReader) {
        username = reader.readVarString()
        hasUUID = reader.readBoolean()
        if (hasUUID) uuid = reader.readUUID()
    }
}

class JavaLoginSuccessPacket(
    val uuid: UUID = UUID.randomUUID(),
    val username: String = ""
): JavaPacket {
    companion object {
        val ID = 0x02
        val TYPE = JavaConnectionState.LOGIN
    }

    override val id: Int = ID

    override fun encode(writer: ByteWriter) {
        writer.writeLong(uuid.mostSignificantBits)
        writer.writeLong(uuid.leastSignificantBits)
        writer.writeString(username)
        writer.writeVarInt(0)
    }

    override fun decode(reader: AbstractReader) {
        throw NotImplementedException("This packet can only be sent!")
    }
}