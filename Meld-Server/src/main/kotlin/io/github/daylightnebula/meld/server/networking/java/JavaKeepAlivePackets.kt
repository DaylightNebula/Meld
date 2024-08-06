package io.github.daylightnebula.meld.server.networking.java

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import kotlin.random.Random

class JavaPlayKeepAlivePacket(
    var randomID: Long = Random.nextLong()
): JavaPacket {
    override val id: Int = 0x24
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
    companion object {
        val ID = 0x05
        val TYPE = JavaConnectionState.CONFIG
    }

    override val id: Int = ID
    override fun encode(writer: ByteWriter) {
        writer.writeLong(randomID)
    }
    override fun decode(reader: AbstractReader) {}
}
