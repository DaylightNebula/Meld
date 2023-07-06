package io.github.daylightnebula.worlds.chunks

import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.player.Player
import org.cloudburstmc.math.vector.Vector2i
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import kotlin.math.floor

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
    var blockPalette: FlexiblePalette = FlexiblePalette.filled()
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

fun Player.getChunkPosition(): Vector2i =
    Vector2i.from(floor(position.x.dec16IfNegative() / 16).toInt(), floor(position.z.dec16IfNegative() / 16).toInt())

fun Vector3i.toChunkPosition(): Vector2i =
    Vector2i.from((x.dec16IfNegative() / 16), (z.dec16IfNegative() / 16))

fun Vector3f.toChunkPosition(): Vector2i =
    Vector2i.from(floor(x.dec16IfNegative() / 16).toInt(), floor(z.dec16IfNegative() / 16).toInt())

fun Int.dec16IfNegative(): Int { return if (this < 0) this - 15 else this }
fun Float.dec16IfNegative(): Float { return if (this < 0) this - 15f else this }

fun Int.inc16IfNegative(): Int { return if (this < 0) this + 15 else this }
fun Float.inc16IfNegative(): Float { return if (this < 0) this + 15f else this }

fun Int.toSectionID(): Int = (this + 64) / 16
fun Float.toSectionID(): Int = floor(this).toInt().toSectionID()