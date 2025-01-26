package io.github.daylightnebula.meld.server.registries.codec

import io.github.daylightnebula.meld.server.generated.Biome
import io.github.daylightnebula.meld.server.registries.RegistryCodec
import net.benwoodworth.knbt.NbtCompound

object BiomeRegistry: RegistryCodec.Codec {
    override fun name() = "minecraft:worldgen/biome"
    override fun build() = Biome.biomes.map { biome -> "minecraft:${biome.name}" to null }

//    val data = RegistryCodec.interpretSnifferData("""{
//        "entries": [
//            {
//                "id": {
//                    "raw_string": "minecraft:badlands"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:bamboo_jungle"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:basalt_deltas"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:beach"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:birch_forest"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:cherry_grove"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:cold_ocean"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:crimson_forest"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:dark_forest"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:deep_cold_ocean"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:deep_dark"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:deep_frozen_ocean"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:deep_lukewarm_ocean"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:deep_ocean"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:desert"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:dripstone_caves"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:end_barrens"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:end_highlands"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:end_midlands"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:eroded_badlands"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:flower_forest"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:forest"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:frozen_ocean"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:frozen_peaks"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:frozen_river"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:grove"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:ice_spikes"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:jagged_peaks"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:jungle"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:lukewarm_ocean"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:lush_caves"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:mangrove_swamp"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:meadow"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:mushroom_fields"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:nether_wastes"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:ocean"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:old_growth_birch_forest"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:old_growth_pine_taiga"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:old_growth_spruce_taiga"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:pale_garden"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:plains"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:river"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:savanna"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:savanna_plateau"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:small_end_islands"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:snowy_beach"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:snowy_plains"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:snowy_slopes"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:snowy_taiga"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:soul_sand_valley"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:sparse_jungle"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:stony_peaks"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:stony_shore"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:sunflower_plains"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:swamp"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:taiga"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:the_end"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:the_void"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:warm_ocean"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:warped_forest"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:windswept_forest"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:windswept_gravelly_hills"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:windswept_hills"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:windswept_savanna"
//                }
//            },
//            {
//                "id": {
//                    "raw_string": "minecraft:wooded_badlands"
//                }
//            }
//        ],
//        "registry": {
//            "raw_string": "minecraft:worldgen/biome"
//        }
//    }""")
}