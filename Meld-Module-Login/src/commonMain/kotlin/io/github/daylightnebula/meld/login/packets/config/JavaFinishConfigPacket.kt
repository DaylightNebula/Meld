package io.github.daylightnebula.meld.login.packets.config

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class JavaFinishConfigPacket(): JavaPacket {
    companion object: JavaPacket.Creator<JavaFinishConfigPacket> {
        override val INCOMING_ID = 0x03
        override val STATE = JavaConnectionState.CONFIG
        override fun create() = JavaFinishConfigPacket()
    }

    override val OUTGOING_ID: Int = 0x03
    override fun encode(writer: ByteWriter) {}
    override fun decode(reader: AbstractReader) {}
}