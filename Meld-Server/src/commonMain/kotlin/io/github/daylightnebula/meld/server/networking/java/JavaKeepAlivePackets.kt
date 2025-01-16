package io.github.daylightnebula.meld.server.networking.java

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import kotlin.random.Random

class JavaPlayKeepAlivePacket(
    var randomID: Long = Random.nextLong()
): JavaPacket {
    override val OUTGOING_ID: Int = 0x27
    override fun encode(writer: ByteWriter) {
        writer.writeLong(randomID)
    }
    override fun decode(reader: AbstractReader) {
        randomID = reader.readLong()
    }
}

class JavaConfigKeepAlivePacket(
    var randomID: Long = Random.nextLong()
): JavaPacket {
    companion object: JavaPacket.Creator<JavaConfigKeepAlivePacket> {
        override val INCOMING_ID = 0x04
        override val STATE = JavaConnectionState.CONFIG
        override fun create() = JavaConfigKeepAlivePacket()
    }

    override val OUTGOING_ID: Int = INCOMING_ID
    override fun encode(writer: ByteWriter) {
        writer.writeLong(randomID)
    }
    override fun decode(reader: AbstractReader) {}
}
