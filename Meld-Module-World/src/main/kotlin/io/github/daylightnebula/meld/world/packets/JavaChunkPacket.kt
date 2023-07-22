package io.github.daylightnebula.meld.world.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.common.DataPacketMode
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.world.chunks.Chunk
import io.github.daylightnebula.meld.world.chunks.ChunkRegistry
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
        writer.writeInt(chunk.position.x)
        writer.writeInt(chunk.position.y)
        writer.writeNBT(heightmaps)
        writer.writeVarInt(data.size)
        writer.writeByteArray(data)

        writer.writeVarInt(0)

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