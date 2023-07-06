package io.github.daylightnebula.worlds

import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.worlds.chunks.FlexiblePalette
import io.github.daylightnebula.worlds.chunks.Section
import io.github.daylightnebula.worlds.chunks.chunk

object World {
    val overworld = Dimension(
        "overworld",
        hashMapOf(
            chunk(0, 0, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(1) else FlexiblePalette.filled()) }),
            chunk(1, 0, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(2) else FlexiblePalette.filled()) }),
            chunk(0, 1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(3) else FlexiblePalette.filled()) }),
            chunk(0, -1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(4) else FlexiblePalette.filled()) }),
            chunk(-1, 0, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(5) else FlexiblePalette.filled()) }),
            chunk(-1, 1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(6) else FlexiblePalette.filled()) }),
            chunk(-1, -1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(7) else FlexiblePalette.filled()) }),
            chunk(1, 1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(8) else FlexiblePalette.filled()) }),
            chunk(1, -1, Array(24) { idx -> Section(if (idx < 3) FlexiblePalette.filled(9) else FlexiblePalette.filled()) }),
        )
    )

    fun init() {
        EventBus.register(overworld)
    }
}