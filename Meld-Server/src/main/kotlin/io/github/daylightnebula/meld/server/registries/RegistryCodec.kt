package io.github.daylightnebula.meld.server.registries

import io.github.daylightnebula.meld.server.meldJson
import io.github.daylightnebula.meld.server.meldNbt
import io.github.daylightnebula.meld.server.utils.NotImplementedException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.*
import kotlinx.serialization.serializer
import net.benwoodworth.knbt.*
import java.io.File
import kotlin.reflect.full.starProjectedType

object RegistryCodec {
    val codec: JsonObject = meldJson.decodeFromString(File("codec.json").readText())


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

    val nbt = StringifiedNbt.decodeFromString<NbtCompound>(File("codec.json").readText())

    fun nbtCompoundList(type: String, vararg elements: NbtCompound): NbtCompound {
        return NbtCompound(mapOf(
            "type" to NbtString(type),
            "value" to NbtList.invoke(elements.toList())
        ))
    }

    fun nbtListElement(index: Int, name: String, element: NbtCompound): NbtCompound {
        return NbtCompound(mapOf(
            "id" to NbtInt(index),
            "name" to NbtString(name),
            "element" to element
        ))
    }

    fun nbtCompound(vararg elements: Pair<String, NbtTag>): NbtCompound {
        return NbtCompound(mapOf(*elements))
    }

    fun nbtCompoundSafe(vararg elements: Pair<String, NbtTag?>): NbtCompound {
        return nbtCompound(*((elements.filter { it.second != null } as List<Pair<String, NbtTag>>).toTypedArray()))
    }

    fun nbtDamageType(
        index: Int,
        name: String,
        scaling: String,
        messageID: String,
        exhaustion: Float
    ) = nbtListElement(index, name, nbtCompoundSafe(
        "scaling" to NbtString(scaling),
        "messageID" to NbtString(messageID),
        "exhaustion" to NbtFloat(exhaustion)
    ))
}

fun JsonObject.toNBT(): NbtTag {
    val type = get("type")!!.jsonPrimitive.content
    val value = get("value")!!.jsonPrimitive
    return when(type) {
        "string" -> NbtString(value.content)
        "float" -> NbtFloat(value.float)
        else -> throw NotImplementedException("TODO damage type converter for type $type")
    }
}