package io.github.daylightnebula.meld.inventories.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode

class JavaSetSelectedSlotPacket(
    var slot: Int = 0
): JavaPacket {
    companion object: JavaPacket.Creator<JavaSetSelectedSlotPacket> {
        override val INCOMING_ID: Int = 0x51
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaSetSelectedSlotPacket()
    }

    override val OUTGOING_ID: Int = 0x51 // id when going to clients, 0x28 when being received
    override fun encode(writer: ByteWriter) {
        writer.writeShort(slot.toShort())
    }
    override fun decode(reader: AbstractReader) {
        slot = reader.readShort().toInt()
    }
}