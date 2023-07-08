package io.github.daylightnebula.worlds

import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.worlds.chunks.FlexiblePalette
import io.github.daylightnebula.worlds.chunks.Section
import io.github.daylightnebula.worlds.chunks.chunk

object World {
    val dimensions = hashMapOf(
        dimension(
            "overworld",
            *(0..441).map { index ->
                val x = index / 21
                val y = index % 21
                chunk(x - 10, y - 10, Array(24) { idx -> Section(if (idx < 6) FlexiblePalette.filled(11) else FlexiblePalette.filled()) })
            }.toTypedArray()
        )
    )

    fun init() {
        dimensions.values.forEach { EventBus.register(it) }
    }
}