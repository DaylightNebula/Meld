package io.github.daylightnebula.meld.login.packets.login

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.noEncode
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class JavaInitiateLoginPacket(
    var username: String = "",
    var uuid: Uuid? = null
): JavaPacket {
    companion object: JavaPacket.Creator<JavaInitiateLoginPacket> {
        override val INCOMING_ID = 0x00
        override val STATE = JavaConnectionState.LOGIN
        override fun create() = JavaInitiateLoginPacket()
    }

    override val OUTGOING_ID: Int = INCOMING_ID
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        username = reader.readVarString()
        uuid = reader.readUUID()
    }
}

@OptIn(ExperimentalUuidApi::class)
class JavaLoginSuccessPacket(
    val uuid: Uuid = Uuid.random(),
    val username: String = "",
    val strictErrorHandling: Boolean = false
): JavaPacket {
    companion object: JavaPacket.Creator<JavaLoginSuccessPacket> {
        override val INCOMING_ID = 0x02
        override val STATE = JavaConnectionState.LOGIN
        override fun create() = JavaLoginSuccessPacket()
    }

    override val OUTGOING_ID: Int = INCOMING_ID

    override fun encode(writer: ByteWriter) {
        uuid.toLongs { most, least ->
            writer.writeLong(most)
            writer.writeLong(least)
        }
        writer.writeString(username)
        writer.writeVarInt(0)
    }

    override fun decode(reader: AbstractReader) = noDecode()
}