package io.github.daylightnebula.meld.server.login.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class JavaStatusPingPacket(var time: Long = System.currentTimeMillis()): JavaPacket {
    companion object {
        val ID = 0x01
        val TYPE = JavaConnectionState.STATUS
    }

    override val id = ID

    override fun encode(writer: ByteWriter) {
        writer.writeLong(time)
    }

    override fun decode(reader: AbstractReader) {
        time = reader.readLong()
    }
}