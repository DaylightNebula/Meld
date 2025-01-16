package io.github.daylightnebula.meld.login.packets.config

import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaSelectKnownPackPacket: JavaPacket {
    companion object: JavaPacket.Creator<JavaSelectKnownPackPacket> {
        override val INCOMING_ID = 0x07
        override val STATE = JavaConnectionState.CONFIG
        override fun create() = JavaSelectKnownPackPacket()
    }

    override val OUTGOING_ID: Int = 0x0E

    override fun decode(reader: AbstractReader) {}

    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(1)
        writer.writeString("minecraft")
        writer.writeString("core")
        writer.writeString(Meld.javaVersion)
    }
}