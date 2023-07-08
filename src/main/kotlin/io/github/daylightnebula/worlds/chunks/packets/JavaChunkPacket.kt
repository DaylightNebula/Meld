package io.github.daylightnebula.worlds.chunks.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.common.DataPacketMode
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode
import io.github.daylightnebula.worlds.chunks.Chunk
import io.github.daylightnebula.worlds.chunks.ChunkRegistry
import org.jglrxavpok.hephaistos.nbt.NBTCompound

class JavaChunkPacket(
    var chunk: Chunk = Chunk(),
    var heightmaps: NBTCompound = ChunkRegistry.defaultHeightmap
): JavaPacket {

    override val id: Int = 0x24
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        // serialize chunk
        val dataWriter = ByteWriter(0, DataPacketMode.JAVA)
        chunk.writeJava(dataWriter)
        val data = dataWriter.getRawData()

        // write chunk header and raw data
        writer.writeInt(chunk.chunkX)
        writer.writeInt(chunk.chunkY)
        writer.writeNBT(heightmaps)
        writer.writeVarInt(data.size)
        writer.writeByteArray(data)

        // TODO block entities here
        writer.writeVarInt(0)

        // TODO light info
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