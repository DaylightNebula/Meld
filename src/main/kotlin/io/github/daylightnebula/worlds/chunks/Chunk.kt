package io.github.daylightnebula.worlds.chunks

import io.github.daylightnebula.networking.common.ByteWriter

data class Chunk(
    var chunkX: Int = 0,
    var chunkY: Int = 0,
    var sections: Array<Section> = Array(24) { Section() }
) {
    fun write(writer: ByteWriter) {
        // synchronously loop over the sections
        for (section in sections)
            section.write(writer)
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chunk

        if (chunkX != other.chunkX) return false
        if (chunkY != other.chunkY) return false
        return sections.contentEquals(other.sections)
    }
    override fun hashCode(): Int {
        var result = chunkX
        result = 31 * result + chunkY
        result = 31 * result + sections.contentHashCode()
        return result
    }
}

data class Section(
    var blockPalette: Palette = FilledPalette()
) {
    fun write(writer: ByteWriter) {
        // write content
        writer.writeShort(blockPalette.count)
        blockPalette.write(writer)

        // empty biomes palette
        writer.writeUByte(0u)
        writer.writeVarInt(0)
        writer.writeVarInt(0)
    }
}

interface Palette {
    val count: Short
    fun write(writer: ByteWriter)
}

class FilledPalette(var id: Int = 0): Palette {
    override val count: Short = if (id == 0) 0 else 1
    override fun write(writer: ByteWriter) {
        writer.writeUByte(0u)      // bits per entry
        writer.writeVarInt(id)          // single value of id
        writer.writeVarInt(0)        // length of data array (none)
    }
}