package io.github.daylightnebula.join

import org.jglrxavpok.hephaistos.nbt.*
import org.jglrxavpok.hephaistos.parser.SNBTParser
import java.io.StringReader
import java.util.Map


object RegistryCodec {

    val chatRegistry = SNBTParser(StringReader("""
        {
            "type": "minecraft:chat_type",
            "value": [
                 {
                    "name":"minecraft:chat",
                    "id":1,
                    "element":{
                       "chat":{
                          "translation_key":"chat.type.text",
                          "parameters":[
                             "sender",
                             "content"
                          ]
                       },
                       "narration":{
                          "translation_key":"chat.type.text.narrate",
                          "parameters":[
                             "sender",
                             "content"
                          ]
                       }
                    }
                 }
            ]
        }""")).parse() as NBTCompound

    val chatRegistry2 = NBTCompound(mapOf(
        "type" to NBTString("minecraft:chat_type"),
        "value" to NBTList(
            NBTType.TAG_Compound,
            listOf(
                    NBTCompound(mapOf(
                    "name" to NBTString("minecraft:chat"),
                    "id" to NBTInt(0),
                    "element" to NBTCompound(mapOf(
                        "chat" to NBTCompound(mapOf(
                            "translation_key" to NBTString("chat.type.text"),
                            "style" to NBTCompound(),
                            "parameters" to NBTList(
                                NBTType.TAG_String,
                                listOf(
                                    NBTString("sender"),
                                    NBTString("content")
                                )
                            )
                        )),
                        "narration" to NBTCompound(mapOf(
                            "translation_key" to NBTString("chat.type.text.narrate"),
                            "style" to NBTCompound(),
                            "parameters" to NBTList(
                                NBTType.TAG_String,
                                listOf(
                                    NBTString("sender"),
                                    NBTString("content")
                                )
                            )
                        ))
                    ))
                ))
            )
        )
    ))

    val dimensionType = NBTCompound(mapOf(
        "type" to NBTString("minecraft:dimension_type"),
        "value" to NBTList(
            NBTType.TAG_Compound,
            listOf(
                NBTCompound(mapOf(
                    "name" to NBTString("minecraft:overworld"),
                    "id" to NBTInt(0),
                    "element" to NBTCompound(mapOf(
                        "piglin_safe" to NBTByte(0x01),
                        "has_raids" to NBTByte(0x01),
                        "monster_spawn_light_level" to NBTInt(7),
                        "monster_spawn_block_light_limit" to NBTInt(7),
                        "natural" to NBTByte(0x01),
                        "ambient_light" to NBTFloat(1.0f),
                        "fixed_time" to NBTLong(4000L),
                        "infiniburn" to NBTString("#minecraft:infiniburn_overworld"),
                        "respawn_anchor_works" to NBTByte(0x01),
                        "has_skylight" to NBTByte(0x01),
                        "bed_works" to NBTByte(0x01),
                        "effects" to NBTString("minecraft:overworld"),
                        "min_y" to NBTInt(-64),
                        "height" to NBTInt(384),
                        "logical_height" to NBTInt(384),
                        "coordinate_scale" to NBTDouble(1.0),
                        "ultrawarm" to NBTByte(0x00),
                        "has_ceiling" to NBTByte(0x00)
                    ))
                ))
            )
        )
    ))

    val defaultBiome = NBTCompound(mapOf(
        "type" to NBTString("minecraft:worldgen/biome"),
        "value" to NBTList(
            NBTType.TAG_Compound,
            listOf(
                NBTCompound(mapOf(
                    "name" to NBTString("minecraft:plains"),
                    "id" to NBTInt(0),
                    "element" to NBTCompound(mapOf(
                        "has_precipitation" to NBTByte(0x01),
                        "temperature" to NBTFloat(1.0f),
                        "downfall" to NBTFloat(0.0f),
                        "effects" to NBTCompound(mapOf(
                            "fog_color" to NBTInt(0xC0D8FF),
                            "sky_color" to NBTInt(0x78A7FF),
                            "water_color" to NBTInt(0x3F76E4),
                            "water_fog_color" to NBTInt(0x50533)
                        ))
                    ))
                ))
            )
        )
    ))

    var nbt: NBTCompound = NBTCompound(
        Map.of(
            "minecraft:chat_type", chatRegistry,
            "minecraft:dimension_type", dimensionType,
            "minecraft:worldgen/biome", defaultBiome
        )
    )
}