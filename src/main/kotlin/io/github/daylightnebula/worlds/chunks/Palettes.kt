package io.github.daylightnebula.worlds.chunks

import io.github.daylightnebula.networking.common.ByteWriter


interface Palette {
    val count: Short
    fun write(writer: ByteWriter)
}

class FilledPalette(var id: Int = 0): Palette {
    override val count: Short get() = if (id == 0) 0 else 1
    override fun write(writer: ByteWriter) {
        writer.writeUByte(0u)      // bits per entry
        writer.writeVarInt(id)          // single value of id
        writer.writeVarInt(0)        // length of data array (none)
    }
}

class FlexiblePalette(
    var blockIDs: IntArray = intArrayOf(0),
    var blockReferences: ByteArray = ByteArray(16 * 16 * 16) { 0 },
    var internalCount: Short = 0
): Palette {
    companion object {
        fun filled(blockID: Int = 0) = FlexiblePalette(
            blockIDs = if (blockID != 0) intArrayOf(0, blockID) else intArrayOf(0),
            blockReferences = ByteArray(16 * 16 * 16) { if (blockID != 0) 1 else 0 },
            internalCount = if (blockID == 0) 0 else 4096
        )
    }

    override val count: Short get() = internalCount
    override fun write(writer: ByteWriter) {
        // 8 bits per entry (byte per block (could this be lowered for compression?))
        writer.writeUByte(8u)

        // write block ids
        writer.writeVarInt(blockIDs.size)
        for (id in blockIDs) writer.writeVarInt(id)

        // write block references
        if (blockReferences.size % 8 != 0) throw IllegalArgumentException("Block references was not divisible by 8.  Recommended size is 4096.")
        writer.writeVarInt(blockReferences.size / 8)
        writer.writeByteArray(blockReferences)
    }
}