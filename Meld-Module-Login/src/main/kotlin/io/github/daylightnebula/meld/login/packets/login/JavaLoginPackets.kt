package io.github.daylightnebula.meld.login.packets.login

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.noEncode
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
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        username = reader.readVarString()
        uuid = reader.readUUID()
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

    override fun decode(reader: AbstractReader) = noDecode()
}