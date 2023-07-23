package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.world.chunks.FlexiblePalette
import io.github.daylightnebula.meld.world.chunks.Section
import io.github.daylightnebula.meld.world.chunks.chunk
import org.cloudburstmc.math.vector.Vector3f

object World {
    val dimensions = hashMapOf(
        dimension(
            "overworld",
            *(0..10000).map { index ->
                // create chunk
                val x = index / 100
                val y = index % 100
                val chunk = chunk(x - 50, y - 50, Array(24) { idx -> Section(if (idx < 6) FlexiblePalette.filled(11) else FlexiblePalette.filled()) })

                // add entities
//                val entityPosition = Vector3f.from((x - 50) * 16f, 33f, (y - 50) * 16f)
//                chunk.second.entities.add(
//                    Entity(
//                        type = EntityType.AXOLOTL,
//                        startPosition = entityPosition
//                    )
//                )

                // return chunk
                chunk
            }.toTypedArray()
        )
    )

    fun init() {}
}