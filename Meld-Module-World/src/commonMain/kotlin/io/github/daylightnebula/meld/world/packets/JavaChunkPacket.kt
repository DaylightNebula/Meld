package io.github.daylightnebula.meld.world.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.common.DataPacketMode
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.world.chunks.Chunk
import io.github.daylightnebula.meld.world.chunks.ChunkRegistry
import net.benwoodworth.knbt.NbtCompound

class JavaChunkPacket(
    var chunk: Chunk = Chunk(),
    var heightmaps: NbtCompound = ChunkRegistry.defaultHeightmap
): JavaPacket {
    companion object: JavaPacket.Creator<JavaChunkPacket> {
        override val INCOMING_ID: Int = 0x28
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaChunkPacket()
    }

    override val OUTGOING_ID: Int = 0x28
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        // serialize chunk
        val dataWriter = ByteWriter(0, DataPacketMode.JAVA)
        chunk.writeJava(dataWriter)
        val data = dataWriter.getRawData()

        // write chunk header and raw data
        writer.writeInt(chunk.position.x.toInt())
        writer.writeInt(chunk.position.y.toInt())
        writer.writeNBT(heightmaps)

        writer.writeVarInt(data.size)
        writer.writeByteArray(data)

        // block entities
        writer.writeVarInt(0)

        // lights
        writer.writeVarInt(2)
        writer.writeLong(0)
        writer.writeLong(0)
        writer.writeVarInt(2)
        writer.writeLong(0)
        writer.writeLong(0)
        writer.writeVarInt(2)
        writer.writeLong(0)
        writer.writeLong(0)
        writer.writeVarInt(2)
        writer.writeLong(0)
        writer.writeLong(0)
        writer.writeVarInt(0)
        writer.writeVarInt(0)
    }
}