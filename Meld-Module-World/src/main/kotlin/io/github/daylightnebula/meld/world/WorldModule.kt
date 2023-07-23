package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.MeldModule
import org.cloudburstmc.math.vector.Vector3f
import kotlin.random.Random

class WorldModule: MeldModule {
    private val testEntities = false
    private val numTestEntitiesPerChunk = 16
    private val testEntitiesHeight = 33f

    override fun onEnable() {
        EventBus.register(WorldListener())
        println("Initializing world...")
        World.init()
        println("Initialized world!")

        // add test entities
        if (testEntities) {
            println("Creating test entities...")

            World.dimensions.forEach { dimension ->
                dimension.value.loadedChunks.forEach {
                    val chunk = it.value
                    (0 until numTestEntitiesPerChunk).forEach { _ ->
                        Entity(
                            type = EntityType.values().random(),
                            startPosition = Vector3f.from(
                                chunk.position.x * 16f + (Random.nextFloat() * 16f),
                                testEntitiesHeight,
                                chunk.position.y * 16f + (Random.nextFloat() * 16f)
                            )
                        )
                    }
                }
            }

            println("Created test entities!")
        }
    }

    override fun onDisable() {}
}