package io.github.daylightnebula.meld.world.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaSetCenterChunkPacket(
    var chunkX: Int = 0,
    var chunkY: Int = 0
): JavaPacket {
    companion object: JavaPacket.Creator<JavaSetCenterChunkPacket> {
        override val INCOMING_ID: Int = 0x58
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaSetCenterChunkPacket()
    }

    override val OUTGOING_ID: Int = 0x58
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(chunkX)
        writer.writeVarInt(chunkY)
    }
}