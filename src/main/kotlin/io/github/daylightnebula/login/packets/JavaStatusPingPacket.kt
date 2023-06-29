package io.github.daylightnebula.login.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaPacket

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