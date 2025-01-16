package io.github.daylightnebula.meld.login.packets.login

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import kotlinx.datetime.Clock

class JavaStatusPingPacket(var time: Long = Clock.System.now().toEpochMilliseconds()): JavaPacket {
    companion object: JavaPacket.Creator<JavaStatusPingPacket> {
        override val INCOMING_ID = 0x01
        override val STATE = JavaConnectionState.STATUS
        override fun create() = JavaStatusPingPacket()
    }

    override val OUTGOING_ID = INCOMING_ID

    override fun encode(writer: ByteWriter) {
        writer.writeLong(time)
    }

    override fun decode(reader: AbstractReader) {
        time = reader.readLong()
    }
}