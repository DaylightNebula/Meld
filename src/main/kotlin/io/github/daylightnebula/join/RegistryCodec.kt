package io.github.daylightnebula.join

import io.github.daylightnebula.nbt.NBT

object RegistryCodec {

//    val registry = SNBTParser(StringReader(
//    """
//        {
//          "minecraft:chat_type": {
//            "value": [
//              {
//                "id": 1,
//                "name": "minecraft:chat",
//                "element": {
//                  "narration": {
//                    "translation_key": "chat.type.text.narrate",
//                    "parameters": [
//                      "sender",
//                      "content"
//                    ]
//                  },
//                  "chat": {
//                    "translation_key": "chat.type.text",
//                    "parameters": [
//                      "sender",
//                      "content"
//                    ]
//                  }
//                }
//              }
//            ],
//            "type": "minecraft:chat_type"
//          },
//          "minecraft:dimension_type": {
//            "value": [
//              {
//                "id": 0,
//                "name": "minecraft:overworld",
//                "element": {
//                  "logical_height": 384,
//                  "effects": "minecraft:overworld",
//                  "height": 384,
//                  "min_y": -64,
//                  "infiniburn": "#minecraft:infiniburn_overworld",
//                  "respawn_anchor_works": 0B,
//                  "monster_spawn_block_light_limit": 0,
//                  "has_raids": 1B,
//                  "has_skylight": 1B,
//                  "natural": 1B,
//                  "monster_spawn_light_level": {
//                    "type": "minecraft:uniform",
//                    "value": {
//                      "max_inclusive": 7,
//                      "min_inclusive": 0
//                    }
//                  },
//                  "ambient_light": 0.0F,
//                  "has_ceiling": 0B,
//                  "bed_works": 1B,
//                  "coordinate_scale": 1.0D,
//                  "ultrawarm": 0B,
//                  "piglin_safe": 0B
//                }
//              }
//            ],
//            "type": "minecraft:dimension_type"
//          },
//          "minecraft:worldgen/biome": {
//            "value": [
//              {
//                "id": 0,
//                "name": "minecraft:plains",
//                "element": {
//                  "effects": {
//                    "fog_color": 12638463,
//                    "water_color": 4159204,
//                    "water_fog_color": 329011,
//                    "sky_color": 7907327
//                  },
//                  "has_precipitation": 1B,
//                  "downfall": 0.4F,
//                  "depth": 0.125F,
//                  "temperature": 0.8F,
//                  "category": "none",
//                  "scale": 0.05F
//                }
//              }
//            ],
//            "type": "minecraft:worldgen/biome"
//          }
//        }
//    """.trimIndent()
//    )).parse() as NBTCompound

    var nbt: NBT.Map<NBT<*>> = NBT.Map(
        mapOf(
            "minecraft:chat_type" to NBT.Map<NBT<*>>(mapOf()),
            "minecraft:dimension_type" to NBT.Map(mapOf()),
            "minecraft:worldgen/biome" to NBT.Map(mapOf())
        )
    )
//        Map.of(
//            "minecraft:chat_type", chatRegistry,
//            "minecraft:dimension_type", dimensionType,
//            "minecraft:worldgen/biome", defaultBiome
//        )
//    )
}