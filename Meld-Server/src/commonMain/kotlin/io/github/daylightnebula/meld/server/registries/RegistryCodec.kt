package io.github.daylightnebula.meld.server.registries

import io.github.daylightnebula.meld.server.meldJson
import kotlinx.serialization.json.*
import net.benwoodworth.knbt.*

object RegistryCodec {
//    val codec: JsonObject = meldJson.decodeFromString(File("codec.json").readText())


//    val chatRegistry = meldNbt.encodeToNbtTag(ChatRegistry.default)
//    val dimensionType = meldNbt.encodeToNbtTag(DimensionRegistry.default)
//    val defaultBiome = meldNbt.encodeToNbtTag(BiomeRegistry.default)
//    val damageTypeJson = codec["value"]!!
//        .jsonObject["minecraft:damage_type"]!!
//        .jsonObject["value"]!!.jsonObject
//        .jsonObject["value"]!!.jsonObject
//        .jsonObject["value"]!!.jsonObject
//        .jsonObject["value"]!!.jsonArray
//    var damageTypes = nbtCompoundList(
//        "minecraft:damage_type",
//        *(damageTypeJson.mapIndexed { index, j ->
//            val json = j.jsonObject
//
//            // unpack json
//            val id = json["id"]!!.jsonObject["value"]!!.jsonPrimitive.int
//            val name = json["name"]!!.jsonObject["value"]!!.jsonPrimitive.content
//            val elements = json["element"]!!.jsonObject["value"]!!.jsonObject
//            val map = mutableMapOf<String, NbtTag>()
//
//            // for each key in object
////            elements.keys.forEach { key ->
////                val nbt = elements[key]!!.jsonObject.toNBT()
////                map[key] = nbt
////            }
//
//            // pass back elements
//            nbtListElement(id, name, NbtCompound(map))
//        }.toTypedArray())
//    )

    interface Codec {
        fun name(): String
        fun build(): List<Pair<String, NbtCompound?>>
    }

    data class SnifferData(
        val name: String,
        val entries: List<Pair<String, NbtCompound?>>
    )

    fun interpretSnifferData(text: String): SnifferData {
        val json = meldJson.decodeFromString<JsonObject>(text)
        val name = json["registry"]!!.jsonObject["raw_string"]!!.jsonPrimitive.content
        val entries = json["entries"]!!.jsonArray.map { entry ->
            val json = entry.jsonObject
            val id = json["id"]!!.jsonObject["raw_string"]!!.jsonPrimitive.content
            val data =
                if (json["data"] != null) interpretSniffer(json["data"]!!) as NbtCompound
                else null
            id to data
        }
        return SnifferData(name, entries)
    }

    private fun interpretSniffer(json: JsonElement): NbtTag =
        if (json is JsonPrimitive)
            if (json.isString) NbtString(json.jsonPrimitive.content)
            else if (json.intOrNull != null) NbtInt(json.jsonPrimitive.int)
            else TODO("Json $json")
        else interpretSnifferObject(json.jsonObject)

    @OptIn(UnsafeNbtApi::class)
    private fun interpretSnifferObject(json: JsonObject): NbtTag {
        val type = json["type"]!!.jsonPrimitive.content
        return when(type) {
            "TagCompound" -> {
                val map = mutableMapOf<String, NbtTag>()
                json["content"]!!.jsonObject.forEach { (key, value) ->
                    map[key] = interpretSniffer(value.jsonObject)
                }
                return NbtCompound(map)
            }

            "TagList" -> {
                val list = mutableListOf<NbtTag>()
                json["content"]!!.jsonArray.forEach { value ->
                    list.add(interpretSniffer(value))
                }
                return NbtList(list)
            }

            "TagString" -> NbtString(json["content"]!!.jsonPrimitive.content)

            else -> TODO("Type $type")
        }
    }

//    fun nbtCompoundList(type: String, vararg elements: NbtCompound): NbtCompound {
//        return NbtCompound(mapOf(
//            "type" to NbtString(type),
//            "value" to NbtList.invoke(elements.toList())
//        ))
//    }
//
//    fun nbtListElement(index: Int, name: String, element: NbtCompound): NbtCompound {
//        return NbtCompound(mapOf(
//            "id" to NbtInt(index),
//            "name" to NbtString(name),
//            "element" to element
//        ))
//    }
//
//    fun nbtCompound(vararg elements: Pair<String, NbtTag>): NbtCompound {
//        return NbtCompound(mapOf(*elements))
//    }
//
//    fun nbtCompoundSafe(vararg elements: Pair<String, NbtTag?>): NbtCompound {
//        return nbtCompound(*((elements.filter { it.second != null } as List<Pair<String, NbtTag>>).toTypedArray()))
//    }
//
//    fun nbtDamageType(
//        index: Int,
//        name: String,
//        scaling: String,
//        messageID: String,
//        exhaustion: Float
//    ) = nbtListElement(index, name, nbtCompoundSafe(
//        "scaling" to NbtString(scaling),
//        "messageID" to NbtString(messageID),
//        "exhaustion" to NbtFloat(exhaustion)
//    ))
}

//fun JsonObject.toNBT(): NbtTag {
//    val type = get("type")!!.jsonPrimitive.content
//    val value = get("value")!!.jsonPrimitive
//    return when(type) {
//        "string" -> NbtString(value.content)
//        "float" -> NbtFloat(value.float)
//        else -> throw NotImplementedException("TODO damage type converter for type $type")
//    }
//}