package io.github.daylightnebula.meld.world.chunks

import io.github.daylightnebula.meld.world.chunks.Section
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.extensions.dec16IfNegative
import io.github.daylightnebula.meld.world.Dimension
import org.cloudburstmc.math.vector.Vector2i
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import kotlin.math.floor

fun chunk(
    chunkX: Int = 0, chunkY: Int = 0, dimension: String,
    sections: Array<Section> = Array(24) { FilledSection() }
) = Vector2i.from(chunkX, chunkY) to Chunk(dimension, Vector2i.from(chunkX, chunkY), sections)

fun Player.getChunkPosition(): Vector2i =
    Vector2i.from(floor(position.x.dec16IfNegative() / 16).toInt(), floor(position.z.dec16IfNegative() / 16).toInt())