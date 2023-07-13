package io.github.daylightnebula.worlds

import io.github.daylightnebula.entities.Entity
import io.github.daylightnebula.entities.EntityType
import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.worlds.chunks.FlexiblePalette
import io.github.daylightnebula.worlds.chunks.Section
import io.github.daylightnebula.worlds.chunks.chunk
import org.cloudburstmc.math.vector.Vector3f

object World {
    val dimensions = hashMapOf(
        dimension(
            "overworld",
            *(0..441).map { index ->
                val x = index / 21
                val y = index % 21
                val chunk = chunk(x - 10, y - 10, Array(24) { idx -> Section(if (idx < 6) FlexiblePalette.filled(11) else FlexiblePalette.filled()) })
                chunk.second.entities.add(Entity( type = EntityType.ARMOR_STAND, startPosition = Vector3f.from(0f, 33f, 0f) ))
                chunk
            }.toTypedArray()
        )
    )

    fun init() {
        dimensions.values.forEach { EventBus.register(it) }
    }
}