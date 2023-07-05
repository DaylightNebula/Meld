package io.github.daylightnebula.player.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
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