package io.github.daylightnebula.meld.core.entities

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.core.player.Player

class JavaEntityStatusPacket(
    var entityID: Int = 0,
    var status: Byte = 0
) : JavaPacket {
    constructor(player: Player): this(player.id, 24)

    override val id: Int = 0x1C
    override fun decode(reader: AbstractReader) = io.github.daylightnebula.meld.server.noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeInt(entityID)
        writer.writeByte(status)
    }
}