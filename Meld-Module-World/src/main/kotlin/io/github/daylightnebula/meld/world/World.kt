package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.world.chunks.FlexiblePalette
import io.github.daylightnebula.meld.world.chunks.Section
import io.github.daylightnebula.meld.world.chunks.chunk
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i

object World {
    val dimensions = hashMapOf(
        dimension(
            "overworld",
            *(0..10000).map { index ->
                // create chunk
                val x = index / 100
                val y = index % 100
                val chunk = chunk(
                    x - 50,
                    y - 50,
                    Array(24) { idx ->
                        Section(if (idx < 6) FlexiblePalette.filled(79) else FlexiblePalette.filled())
                    }
                )
                chunk.second.clear(Vector3i.from(1, -63, 1), Vector3i.from(14, 40, 14))
//                val chunk = chunk(x - 50, y - 50, Array(24) { Section(FlexiblePalette.filled()) })
//                chunk.second.fill(Vector3i.from(0, -64, 0), Vector3i.from(15, 30, 15), 11)

                // return chunk
                chunk
            }.toTypedArray()
        )
    )

    fun init() {}
}