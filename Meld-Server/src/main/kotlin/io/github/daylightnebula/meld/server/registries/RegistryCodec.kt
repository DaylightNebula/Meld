package io.github.daylightnebula.meld.server.registries

import io.github.daylightnebula.meld.server.utils.NotImplementedException
import org.jglrxavpok.hephaistos.nbt.*
import org.jglrxavpok.hephaistos.parser.SNBTParser
import org.json.JSONObject
import java.io.File
import java.io.StringReader
import java.math.BigDecimal
import java.util.Map


object RegistryCodec {
    val codec = JSONObject(File("codec.json").readText())

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
    val damageTypeJson = codec.getJSONObject("value").getJSONObject("minecraft:damage_type").getJSONObject("value").getJSONObject("value").getJSONObject("value").getJSONArray("value")
    var damageTypes = nbtTypedList(
        "minecraft:damage_type",
        *(damageTypeJson.mapIndexed { index, json ->
            json as JSONObject

            // unpack json
            val id = json.getJSONObject("id").getInt("value")
            val name = json.getJSONObject("name").getString("value")
            val elements = json.getJSONObject("element").getJSONObject("value")
            val map = mutableMapOf<String, NBT>()

            // for each key in object
            elements.keys().forEach { key ->
                val nbt = elements.getJSONObject(key).toNBT()
                map[key] = nbt
            }

            // pass back elements
            nbtListElement(id, name, NBTCompound(map))
        }.toTypedArray())
    )

    var nbt: NBTCompound = NBTCompound(
        mapOf(
            "minecraft:chat_type" to chatRegistry,
            "minecraft:dimension_type" to dimensionType,
            "minecraft:worldgen/biome" to defaultBiome,
            "minecraft:damage_type" to damageTypes
        )
    )

    fun nbtTypedList(type: String, vararg elements: NBT): NBTCompound {
        return NBTCompound(mapOf(
            "type" to NBTString(type),
            "value" to NBTList(NBTType.TAG_Compound, elements.toList())
        ))
    }

    fun nbtListElement(index: Int, name: String, element: NBTCompound): NBTCompound {
        return NBTCompound(mapOf(
            "id" to NBTInt(index),
            "name" to NBTString(name),
            "element" to element
        ))
    }

    fun nbtCompound(vararg elements: Pair<String, NBT>): NBTCompound {
        return NBTCompound(mapOf(*elements))
    }

    fun nbtCompoundSafe(vararg elements: Pair<String, NBT?>): NBTCompound {
        return nbtCompound(*((elements.filter { it.second != null } as List<Pair<String, NBT>>).toTypedArray()))
    }

    fun nbtDamageType(
        index: Int,
        name: String,
        scaling: String,
        messageID: String,
        exhaustion: Float
    ) = nbtListElement(index, name, nbtCompoundSafe(
        "scaling" to NBTString(scaling),
        "messageID" to NBTString(messageID),
        "exhaustion" to NBTFloat(exhaustion)
    )
    )
}

fun JSONObject.toNBT(): NBT {
    val type = getString("type")
    val value = get("value")
    return when(type) {
        "string" -> NBTString(value as String)
        "float" -> NBTFloat(when (value) {
            is Float -> value
            is BigDecimal -> value.toFloat()
            is Int -> value.toFloat()
            else -> throw NotImplementedException("No float converter for $value")
        })
        else -> throw NotImplementedException("TODO damage type converter for type $type")
    }
}