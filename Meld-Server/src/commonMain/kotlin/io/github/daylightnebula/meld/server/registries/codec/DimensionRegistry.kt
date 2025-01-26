package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.registries.RegistryCodec
import kotlinx.serialization.SerialName

object DimensionRegistry: RegistryCodec.Codec {
    override fun name() = "minecraft:dimension_type"
    override fun build() =
        listOf(
            "minecraft:overworld" to null,
            "minecraft:overworld_caves" to null,
            "minecraft:the_end" to null,
            "minecraft:the_nether" to null
        )

//    val type: String = "minecraft:dimension_type",
//    val value: List<DimensionRegistryEntry>
//    companion object {
//        val default = Meld.json.decodeFromString<DimensionRegistry>("""
//            {
//              "type": "minecraft:dimension_type",
//              "value": [
//                {
//                  "name": "minecraft:overworld",
//                  "id": 0,
//                  "element": {
//                  }
//                }
//              ]
//            }
//        """.trimIndent())
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
//    }
}