package io.github.daylightnebula.worlds.chunks

import io.github.daylightnebula.networking.common.ByteWriter
import org.cloudburstmc.math.vector.Vector2i

// TODO set block function
// TODO get block function
// TODO fill blocks function
// TODO clear blocks function
// TODO broadcast changes
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

fun chunk(
    chunkX: Int = 0, chunkY: Int = 0,
    sections: Array<Section> = Array(24) { Section() }
) = Vector2i.from(chunkX, chunkY) to Chunk(chunkX, chunkY, sections)

data class Section(
    var blockPalette: Palette = FlexiblePalette.filled()
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