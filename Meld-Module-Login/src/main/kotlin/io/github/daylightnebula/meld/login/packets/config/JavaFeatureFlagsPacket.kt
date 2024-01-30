package io.github.daylightnebula.meld.login.packets.config

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaFeatureFlagsPacket(var features: Array<String> = arrayOf("minecraft:vanilla")): JavaPacket {
    override val id: Int = 0x08
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        println("Sending ${features.toList()}")
        writer.writeVarInt(features.size)
        features.forEach { writer.writeString(it) }
    }
}
