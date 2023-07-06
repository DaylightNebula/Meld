package io.github.daylightnebula.worlds

import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.worlds.chunks.FlexiblePalette
import io.github.daylightnebula.worlds.chunks.Section
import io.github.daylightnebula.worlds.chunks.chunk

object World {
    val dimensions = hashMapOf(
        dimension(
            "overworld",
            chunk(0, 0, Array(24) { idx -> Section(if (idx < 1) FlexiblePalette.filled(10) else FlexiblePalette.filled()) }),
//            chunk(1, 0, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(11) else FlexiblePalette.filled()) }),
//            chunk(0, 1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(12) else FlexiblePalette.filled()) }),
//            chunk(0, -1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(13) else FlexiblePalette.filled()) }),
//            chunk(-1, 0, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(14) else FlexiblePalette.filled()) }),
//            chunk(-1, 1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(15) else FlexiblePalette.filled()) }),
//            chunk(-1, -1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(16) else FlexiblePalette.filled()) }),
//            chunk(1, 1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(17) else FlexiblePalette.filled()) }),
//            chunk(1, -1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(18) else FlexiblePalette.filled()) }),
        )
    )

    fun init() {
        dimensions.values.forEach { EventBus.register(it) }
    }
}