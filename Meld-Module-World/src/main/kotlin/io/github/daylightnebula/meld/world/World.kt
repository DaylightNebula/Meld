package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.world.chunks.FlexiblePalette
import io.github.daylightnebula.meld.world.chunks.Section
import io.github.daylightnebula.meld.world.chunks.chunk

object World {
    val dimensions = hashMapOf(
        dimension(
            "overworld",
            *(0..441).map { index ->
                val x = index / 21
                val y = index % 21
                val chunk = chunk(x - 10, y - 10, Array(24) { idx -> Section(if (idx < 6) FlexiblePalette.filled(11) else FlexiblePalette.filled()) })
//                chunk.second.entities.add(Entity( type = EntityType.AXOLOTL, startPosition = Vector3f.from(-3f, 33f, 0f) ))
                chunk
            }.toTypedArray()
        )
    )

    fun init() {
        dimensions.values.forEach { EventBus.register(it) }
    }
}