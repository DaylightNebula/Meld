package io.github.daylightnebula.player

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noEncode

class JavaSetPlayerPosition(
    var x: Double = 0.0,
    var y: Double = 0.0,
    var z: Double = 0.0,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x14
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        x = reader.readDouble()
        y = reader.readDouble()
        z = reader.readDouble()
        onGround = reader.readBoolean()
    }
}