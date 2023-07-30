package io.github.daylightnebula.meld.world.chunks

import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.world.World
import org.cloudburstmc.math.vector.Vector2i
import kotlin.text.Typography.section

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
    var chunkPos: Vector2i = Vector2i.ZERO,
    var sectionIndex: Int = 0
): Section {
    override var blockPalette: FlexiblePalette? = null
        get() = World.dimensions[dimensionRef]?.loadedChunks?.get(chunkPos)?.sections?.get(sectionIndex)?.blockPalette

    override fun writeJava(writer: ByteWriter) {
        // get section and write it to output
        val section = World.dimensions[dimensionRef]?.loadedChunks?.get(chunkPos)?.sections?.get(sectionIndex)
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