package io.github.daylightnebula.entities

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode
import io.github.daylightnebula.player.Player

class JavaEntityStatusPacket(
    var entityID: Int = 0,
    var status: Byte = 0
) : JavaPacket {
    constructor(player: Player): this(player.id, 24)

    override val id: Int = 0x1C
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeInt(entityID)
        writer.writeByte(status)
    }
}