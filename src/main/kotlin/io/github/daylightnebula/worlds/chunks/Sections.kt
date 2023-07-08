package io.github.daylightnebula.worlds.chunks

import io.github.daylightnebula.networking.common.ByteWriter

data class Section(
    var blockPalette: FlexiblePalette = FlexiblePalette.filled()
) {
    fun writeJava(writer: ByteWriter) {
        // write content
        writer.writeShort(blockPalette.count)
        blockPalette.write(writer)

        // empty biomes palette
        writer.writeUByte(0u)
        writer.writeVarInt(0)
        writer.writeVarInt(0)
    }
}