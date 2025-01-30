package io.github.daylightnebula.meld.server.world.chunks

import dev.romainguy.kotlin.math.Float2
import io.github.daylightnebula.meld.server.entities.Player
import io.github.daylightnebula.meld.server.utils.dec16IfNegative
import kotlin.math.floor

fun chunk(
    chunkX: Int = 0, chunkY: Int = 0, dimension: String,
    sections: Array<Section> = Array(24) { FilledSection() }
) = Float2(chunkX.toFloat(), chunkY.toFloat()) to Chunk(dimension, Float2(chunkX.toFloat(), chunkY.toFloat()), sections)

fun Player.getChunkPosition(): Float2 =
    Float2(floor(position.x.dec16IfNegative() / 16), floor(position.z.dec16IfNegative() / 16))