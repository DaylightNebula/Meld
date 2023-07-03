package io.github.daylightnebula.chunks

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noDecode
import org.jglrxavpok.hephaistos.nbt.NBT
import org.jglrxavpok.hephaistos.nbt.NBTCompound

class JavaChunkPacket(
    var chunkX: Int = 0,
    var chunkY: Int = 0,
    var heightmaps: NBTCompound = ChunkRegistry.defaultHeightmap,
    var data: ByteArray = ChunkRegistry.emptyChunk
): JavaPacket {

    override val id: Int = 0x24
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeInt(chunkX)
        writer.writeInt(chunkY)
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