package io.github.daylightnebula.meld.server.world

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.world.chunks.FilledSection
import io.github.daylightnebula.meld.server.world.chunks.FlexiblePalette
import io.github.daylightnebula.meld.server.world.chunks.chunk

object World {
    val dimensions = hashMapOf(
        "overworld" to Dimension(
            "overworld",
            (0..10000).associate { index ->
                // create chunk
                val x = index / 100
                val y = index % 100
                val chunk = chunk(
                    x - 50,
                    y - 50,
                    "overworld",
                    Array(24) { idx ->
                        FilledSection(if (idx < 6) FlexiblePalette.filled(3) else FlexiblePalette.filled())
                    }
                )

                // return chunk
                chunk
            }.toMutableMap()
        )
    )

    fun getBlock(dimension: String, position: Float3) = dimensions[dimension]?.getBlock(position)
    fun setBlock(dimension: String, position: Float3, blockID: Int) = dimensions[dimension]?.setBlock(position, blockID)
    fun fillBlocks(dimension: String, from: Float3, to: Float3, blockID: Int) = dimensions[dimension]?.fillBlocks(from, to, blockID)
    fun clearBlocks(dimension: String, from: Float3, to: Float3) = dimensions[dimension]?.clearBlocks(from, to)
    fun getBlock(dimension: Dimension, position: Float3) = dimension.getBlock(position)
    fun setBlock(dimension: Dimension, position: Float3, blockID: Int) = dimension.setBlock(position, blockID)
    fun fillBlocks(dimension: Dimension, from: Float3, to: Float3, blockID: Int) = dimension.fillBlocks(from, to, blockID)
    fun clearBlocks(dimension: Dimension, from: Float3, to: Float3) = dimension.clearBlocks(from, to)

    fun init() {}
}