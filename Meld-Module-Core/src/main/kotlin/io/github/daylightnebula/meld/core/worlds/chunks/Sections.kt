package io.github.daylightnebula.meld.core.worlds.chunks

import io.github.daylightnebula.meld.server.networking.common.ByteWriter

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