package io.github.daylightnebula.meld.server.registries

import io.github.daylightnebula.meld.server.meldJson
import kotlinx.serialization.SerialName
import kotlin.math.log

data class DimensionRegistry(
    val type: String = "minecraft:dimension_type",
    val value: List<DimensionRegistryEntry>
) {
    companion object {
        val default = meldJson.decodeFromString<DimensionRegistry>("""
            {
              "type": "minecraft:dimension_type",
              "value": [
                {
                  "name": "minecraft:overworld",
                  "id": 0,
                  "element": {
                  }
                }
              ]
            }
        """.trimIndent())
//        val default = DimensionRegistry(
//            value = listOf(
//                DimensionRegistryEntry(
//                    name = "minecraft:overworld",
//                    id = 0,
//                    element = DimensionRegistryElement(
//                        piglinSafe = 0x01,
//                        hasRaids = 0x01,
//                        monsterSpawnLightLevel = 7,
//                        monsterSpawnBlockLightLimit = 7,
//                        natural = 0x01,
//                        ambientLight = 1f,
//                        fixedTime = 4000L,
//                        infiniburn = "#minecraft:infiniburn_overworld",
//                        respawnAnchorWorks = 0x01,
//                        hasSkylight = 0x01,
//                        bedWorks = 0x01,
//                        effects = "minecraft:overworld",
//                        minY = -64,
//                        height = 384,
//                        logicalHeight = 384,
//                        coordinateScale = 1.0,
//                        ultrawarm = 0x00,
//                        hasCeiling = 0x00
//                    )
//                )
//            )
//        )
    }
}

data class DimensionRegistryEntry(
    val name: String,
    val id: Int,
    val element: DimensionRegistryElement
)

data class DimensionRegistryElement(
    @SerialName("piglin_safe")
    val piglinSafe: Byte,
    @SerialName("has_raids")
    val hasRaids: Byte,
    @SerialName("monster_spawn_light_level")
    val monsterSpawnLightLevel: Int,
    @SerialName("monster_spawn_block_light_limit")
    val monsterSpawnBlockLightLimit: Int,
    @SerialName("natural")
    val natural: Byte,
    @SerialName("ambient_light")
    val ambientLight: Float,
    @SerialName("fixed_time")
    val fixedTime: Long,
    @SerialName("infiniburn")
    val infiniburn: String,
    @SerialName("respawn_anchor_works")
    val respawnAnchorWorks: Byte,
    @SerialName("has_skylight")
    val hasSkylight: Byte,
    @SerialName("bed_works")
    val bedWorks: Byte,
    @SerialName("effects")
    val effects: String,
    @SerialName("min_y")
    val minY: Int,
    @SerialName("height")
    val height: Int,
    @SerialName("logical_height")
    val logicalHeight: Int,
    @SerialName("coordinate_scale")
    val coordinateScale: Double,
    @SerialName("ultrawarm")
    val ultrawarm: Byte,
    @SerialName("has_ceiling")
    val hasCeiling: Byte
)