package io.github.daylightnebula.join

import org.jglrxavpok.hephaistos.nbt.*
import org.jglrxavpok.hephaistos.parser.SNBTParser
import java.io.StringReader
import java.util.Map


object RegistryCodec {

    val registry = SNBTParser(StringReader(
    """
        {
          "minecraft:chat_type": {
            "value": [
              {
                "id": 1,
                "name": "minecraft:chat",
                "element": {
                  "narration": {
                    "translation_key": "chat.type.text.narrate",
                    "parameters": [
                      "sender",
                      "content"
                    ]
                  },
                  "chat": {
                    "translation_key": "chat.type.text",
                    "parameters": [
                      "sender",
                      "content"
                    ]
                  }
                }
              }
            ],
            "type": "minecraft:chat_type"
          },
          "minecraft:dimension_type": {
            "value": [
              {
                "id": 0,
                "name": "minecraft:overworld",
                "element": {
                  "name": "minecraft:overworld",
                  "logical_height": 384,
                  "effects": "minecraft:overworld",
                  "height": 384,
                  "min_y": -64,
                  "infiniburn": "#minecraft:infiniburn_overworld",
                  "respawn_anchor_works": 0B,
                  "monster_spawn_block_light_limit": 0,
                  "has_raids": 1B,
                  "has_skylight": 1B,
                  "natural": 1B,
                  "monster_spawn_light_level": 11,
                  "ambient_light": 0.0F,
                  "has_ceiling": 0B,
                  "bed_works": 1B,
                  "coordinate_scale": 1.0D,
                  "ultrawarm": 0B,
                  "piglin_safe": 0B
                }
              }
            ],
            "type": "minecraft:dimension_type"
          },
          "minecraft:worldgen/biome": {
            "value": [
              {
                "id": 0,
                "name": "minecraft:plains",
                "element": {
                  "effects": {
                    "fog_color": 12638463,
                    "water_color": 4159204,
                    "water_fog_color": 329011,
                    "sky_color": 7907327
                  },
                  "precipitation": "rain",
                  "downfall": 0.4F,
                  "depth": 0.125F,
                  "temperature": 0.8F,
                  "category": "none",
                  "scale": 0.05F
                }
              },
              {
                "id": 1,
                "name": "minecraft:swamp",
                "element": {
                  "effects": {
                    "fog_color": 12638463,
                    "water_color": 4159204,
                    "water_fog_color": 329011,
                    "sky_color": 7907327
                  },
                  "precipitation": "rain",
                  "downfall": 0.8F,
                  "depth": 0.2F,
                  "temperature": 0.25F,
                  "category": "none",
                  "scale": 0.2F
                }
              },
              {
                "id": 2,
                "name": "minecraft:swamp_hills",
                "element": {
                  "effects": {
                    "fog_color": 12638463,
                    "water_color": 4159204,
                    "water_fog_color": 329011,
                    "sky_color": 7907327
                  },
                  "precipitation": "rain",
                  "downfall": 0.8F,
                  "depth": 0.2F,
                  "temperature": 0.25F,
                  "category": "none",
                  "scale": 0.2F
                }
              }
            ],
            "type": "minecraft:worldgen/biome"
          }
        }
    """.trimIndent()
    )).parse() as NBTCompound

//    val chatRegistry = NBT.Compound { root ->
//        root.setString("type", "minecraft:chat_type");
//        root.set("value", NBT.List(
//            NBTType.TAG_Compound,
//            listOf(
//                NBT.Compound { inner ->
//                    inner.setString("name", "minecraft:chat")
//                    inner.setInt("id", 0)
//                    inner.set("element", NBT.Compound { element ->
//                        element.set("chat", NBT.Compound { chat ->
//                            chat.setString("translation_key", "chat.type.text")
//                            chat.set("style", NBT.Compound {})
//                            chat.set("parameters", NBT.List(
//                                NBTType.TAG_Compound,
//                                listOf(
//                                    NBT.String("sender"),
//                                    NBT.String("content")
//                                )
//                            ))
//                        })
//                        element.set("narration", NBT.Compound { narration ->
//                            narration.setString("translation_key", "chat.type.text.narrate")
//                            narration.set("style", NBT.Compound {})
//                            narration.set("parameters", NBT.List(
//                                NBTType.TAG_Compound,
//                                listOf(
//                                    NBT.String("sender"),
//                                    NBT.String("content")
//                                )
//                            ))
//                        })
//                    })
//                }
//            )
//        ))
//    }
//
//    val dimensionType = NBT.Compound { root ->
//        root.setString("type", "minecraft:dimension_type")
//        root.set("value", NBTList(
//            NBTType.TAG_Compound,
//            listOf(
//                NBT.Compound { inner ->
//                    inner.setString("name", "minecraft:overworld")
//                    inner.setInt("id", 0)
//                    inner.set("element", NBT.Compound { element ->
//                        element.setByte("piglin_safe", 0x01)
//                        element.setByte("has_raids", 0x01)
//                        element.set("monster_spawn_light_level", NBT.Compound { light ->
//                            light.setString("type", "minecraft:uniform")
//                            light.set("value", NBT.Compound {
//                                light.setInt("max_inclusive", 7)
//                                light.setInt("min_inclusive", 0)
//                            })
//                        })
//                        element.setInt("monster_spawn_block_light_limit", 0)
//                        element.setByte("natural", 0x01)
//                        element.setFloat("ambient_light", 1f)
//                        element.setLong("fixed_time", 4000L)
//                        element.setString("infiniburn", "#minecraft:infiniburn_overworld")
//                        element.setByte("respawn_anchor_works", 0x01)
//                        element.setByte("has_skylight", 0x01)
//                        element.setByte("bed_works", 0x01)
//                        element.setString("effects", "minecraft:overworld")
//                        element.setInt("min_y", -64)
//                        element.setInt("height", 384)
//                        element.setInt("logical_height", 384)
//                        element.setDouble("coordinate_scale", 1.0)
//                        element.setByte("ultrawarm", 0x00)
//                        element.setByte("has_ceiling", 0x00)
//                    })
//                }
//            )
//        ))
//    }
//
//    val defaultBiome = NBT.Compound { root ->
//        root.setString("type", "minecraft:worldgen/biome")
//        root.set("value", NBTList(
//            NBTType.TAG_Compound,
//            listOf(
//                NBT.Compound { inner ->
//                    inner.setString("name", "minecraft:plains")
//                    inner.setInt("id", 0)
//                    inner.set("element", NBT.Compound { element ->
//                        // climate settings
//                        element.setByte("has_precipitation", 0x01)
//                        element.setFloat("temperature", 1f)
//                        element.setFloat("downfall", 0.4f)
//
//                        // effects
//                        element.set("effects", NBT.Compound { effects ->
//                            effects.setInt("fog_color", 0xC0D8FF)
//                            effects.setInt("sky_color", 0x78A7FF)
//                            effects.setInt("water_color", 0x3F76E4)
//                            effects.setInt("water_fog_color", 0x505333)
//                        })
//
////                        element.setFloat("depth", 0.2f)
////                        element.setFloat("scale", 0.2f)
////                        element.setString("precipitation", "rain")
////                        element.setFloat("temperature", 1f)
////                        element.setFloat("downfall", 0.4f)
////                        element.setString("category", "plains")
//                    })
//                }
//            ))
//        )
//    }

    var nbt: NBTCompound = registry //NBTCompound(
//        Map.of(
//            "minecraft:chat_type", chatRegistry,
//            "minecraft:dimension_type", dimensionType,
//            "minecraft:worldgen/biome", defaultBiome
//        )
//    )
}