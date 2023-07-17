package io.github.daylightnebula.meld.core.worlds

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.core.worlds.chunks.FlexiblePalette
import io.github.daylightnebula.meld.core.worlds.chunks.Section
import io.github.daylightnebula.meld.world.chunks.chunk
import org.cloudburstmc.math.vector.Vector3f
import java.lang.Thread.sleep
import kotlin.concurrent.thread

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