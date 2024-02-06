package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.world.anvil.loadRegionFiles
import io.github.daylightnebula.meld.world.chunks.FilledSection
import io.github.daylightnebula.meld.world.chunks.FlexiblePalette
import io.github.daylightnebula.meld.world.chunks.Section
import io.github.daylightnebula.meld.world.chunks.chunk
import org.cloudburstmc.math.vector.Vector2i
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i
import java.io.File

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
                        FilledSection(if (idx < 6) FlexiblePalette.filled(79) else FlexiblePalette.filled())
                    }
                )

                // return chunk
                chunk
            }.toMutableMap()
        )
    )

    fun getBlock(dimension: String, position: Vector3i) = dimensions[dimension]?.getBlock(position)
    fun setBlock(dimension: String, position: Vector3i, blockID: Int) = dimensions[dimension]?.setBlock(position, blockID)
    fun fillBlocks(dimension: String, from: Vector3i, to: Vector3i, blockID: Int) = dimensions[dimension]?.fillBlocks(from, to, blockID)
    fun clearBlocks(dimension: String, from: Vector3i, to: Vector3i) = dimensions[dimension]?.clearBlocks(from, to)
    fun getBlock(dimension: Dimension, position: Vector3i) = dimension.getBlock(position)
    fun setBlock(dimension: Dimension, position: Vector3i, blockID: Int) = dimension.setBlock(position, blockID)
    fun fillBlocks(dimension: Dimension, from: Vector3i, to: Vector3i, blockID: Int) = dimension.fillBlocks(from, to, blockID)
    fun clearBlocks(dimension: Dimension, from: Vector3i, to: Vector3i) = dimension.clearBlocks(from, to)

    fun init() {}
}