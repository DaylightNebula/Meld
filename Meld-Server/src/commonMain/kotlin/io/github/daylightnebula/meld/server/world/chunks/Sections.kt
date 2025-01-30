package io.github.daylightnebula.meld.server.world.chunks

import dev.romainguy.kotlin.math.Float2
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.world.World

interface Section {
    var blockPalette: FlexiblePalette?
    fun writeJava(writer: ByteWriter)
}

class FilledSection(
    override var blockPalette: FlexiblePalette? = FlexiblePalette.filled()
): Section {
    override fun writeJava(writer: ByteWriter) {
        // write content
        writer.writeShort(blockPalette!!.count)
        blockPalette!!.write(writer)

        // empty biomes palette
        writer.writeUByte(0u)
        writer.writeVarInt(0)
        writer.writeVarInt(0)
    }
}

class GhostSection(
    var dimensionRef: String = "",
    var chunkPos: Float2 = Float2(),
    var sectionIndex: Int = 0
): Section {
    override var blockPalette: FlexiblePalette? = null
        get() = World.dimensions[dimensionRef]?.getChunk(chunkPos)?.sections?.get(sectionIndex)?.blockPalette

    override fun writeJava(writer: ByteWriter) {
        // get section and write it to output
        val section = World.dimensions[dimensionRef]?.getChunk(chunkPos)?.sections?.get(sectionIndex)
        section?.writeJava(writer)

        // if no section was found, write blank chunk
        if (section == null) {
            writer.writeShort(0)
            writer.writeUByte(0u)
            writer.writeVarInt(0)
            writer.writeVarInt(0)
        }
    }
}