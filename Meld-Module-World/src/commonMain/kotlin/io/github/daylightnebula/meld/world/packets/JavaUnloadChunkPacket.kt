package io.github.daylightnebula.meld.world.packets

import dev.romainguy.kotlin.math.Float2
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode

class JavaUnloadChunkPacket(
    var chunkPos: Float2 = Float2()
): JavaPacket {
    companion object: JavaPacket.Creator<JavaUnloadChunkPacket> {
        override val INCOMING_ID: Int = 0x22
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaUnloadChunkPacket()
    }

    override val OUTGOING_ID: Int = 0x22
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeInt(chunkPos.x.toInt())
        writer.writeInt(chunkPos.y.toInt())
    }
}