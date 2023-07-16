package io.github.daylightnebula.meld.server.player.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import kotlin.random.Random

class JavaKeepAlivePacket(
    var randomID: Long = Random.nextLong()
): JavaPacket {
    override val id: Int = 0x23
    override fun encode(writer: ByteWriter) {
        writer.writeLong(randomID)
    }
    override fun decode(reader: AbstractReader) {
        randomID = reader.readLong()
    }
}