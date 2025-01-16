package io.github.daylightnebula.meld.world

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.MeldModule
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class WorldModule: MeldModule {
    private val testEntities = false
    private val numTestEntitiesPerChunk = 16
    private val testEntitiesHeight = 33f
    var broadcastEnabled = false

    companion object {
        lateinit var module: WorldModule
    }

    override fun onEnable() {
        module = this
        EventBus.register(WorldListener())
        println("Initializing world...")
        World.init()
        println("Initialized world!")

        // add test entities
        if (testEntities) {
            println("Creating test entities...")

            World.dimensions.forEach { dimension ->
                dimension.value.getChunks().forEach { chunk ->
                    (0 until numTestEntitiesPerChunk).forEach { _ ->
                        Entity(
                            type = EntityType.values().random(),
                            startPosition = Float3(
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

        broadcastEnabled = true
    }

    override fun onDisable() {}
}