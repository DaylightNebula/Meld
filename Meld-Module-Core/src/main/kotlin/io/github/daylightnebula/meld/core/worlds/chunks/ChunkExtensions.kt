package io.github.daylightnebula.meld.core.worlds.chunks

import io.github.daylightnebula.meld.core.player.Player
import org.cloudburstmc.math.vector.Vector2i
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import kotlin.math.floor

fun chunk(
    chunkX: Int = 0, chunkY: Int = 0,
    sections: Array<Section> = Array(24) { Section() }
) = Vector2i.from(chunkX, chunkY) to Chunk(chunkX, chunkY, sections)

fun Player.getChunkPosition(): Vector2i =
    Vector2i.from(floor(position.x.dec16IfNegative() / 16).toInt(), floor(position.z.dec16IfNegative() / 16).toInt())

fun Vector3i.toChunkPosition(): Vector2i =
    Vector2i.from((x.dec16IfNegative() / 16), (z.dec16IfNegative() / 16))

fun Vector3f.toChunkPosition(): Vector2i =
    Vector2i.from(floor(x.dec16IfNegative() / 16).toInt(), floor(z.dec16IfNegative() / 16).toInt())

fun Int.dec16IfNegative(): Int { return if (this < 0) this - 15 else this }
fun Float.dec16IfNegative(): Float { return if (this < 0) this - 15f else this }

fun Int.inc16IfNegative(): Int { return if (this < 0) this + 16 else this }
fun Float.inc16IfNegative(): Float { return if (this < 0) this + 16f else this }

fun Int.toSectionID(): Int = (this + 64) / 16
fun Float.toSectionID(): Int = floor(this).toInt().toSectionID()