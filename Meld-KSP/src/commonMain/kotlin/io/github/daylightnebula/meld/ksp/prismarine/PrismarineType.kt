package io.github.daylightnebula.meld.ksp.prismarine

import io.github.daylightnebula.meld.ksp.MeldProcessor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonPrimitive

@Serializable(with = PrismarineType.Companion::class)
abstract class PrismarineType {
    companion object: KSerializer<PrismarineType> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ProtocolType")

        // Deserialize ProtocolType
        override fun deserialize(decoder: Decoder): PrismarineType {
            val input = decoder as? JsonDecoder
                ?: throw IllegalStateException("This serializer only works with JSON.")
            val element: JsonElement = input.decodeJsonElement()

            return when(element) {
                is JsonPrimitive -> Simple(element.content)
                is JsonObject -> Complex(element)
                is JsonArray -> {
                    val subtype = element[0].jsonPrimitive.content

                    // if we already have a codec for this array type, return a simple type
                    if (MeldProcessor.codecs.containsKey(subtype)) return Simple(subtype)

                    // do final subtype
                    when(subtype) {
                        "mapper", "mapping" -> Json.decodeFromJsonElement<Mapping>(element[1])
                        "switch" -> Json.decodeFromJsonElement<Switch>(element[1])
                        "container" -> Container(Json.decodeFromJsonElement<List<NamedType>>(element[1]))
                        "buffer" -> Json.decodeFromJsonElement<Buffer>(element[1])
                        "array" -> Json.decodeFromJsonElement<Array>(element[1])
                        "option" -> Option(Json.decodeFromJsonElement(element[1]))
                        "bitflags" -> Json.decodeFromJsonElement<BitFlags>(element[1])
                        "bitfield" -> BitFields(Json.decodeFromJsonElement<List<BitField>>(element[1]))
                        "topBitSetTerminatedArray" -> Json.decodeFromJsonElement<TopBitSetTerminatedArray>(element[1])
                        "registryEntryHolderSet" -> Json.decodeFromJsonElement<RegistryEntryHolderSet>(element[1]) // todo may be able to removed this
                        "registryEntryHolder" -> Json.decodeFromJsonElement<RegistryEntryHolder>(element[1])
                        "entityMetadataLoop" -> Json.decodeFromJsonElement<EntityMetadataLoop>(element[1])
                        else -> throw IllegalStateException("ProtocolType Array could not be decoded! ${element[0].jsonPrimitive.content}")
                    }
                }
            }
        }

        // Serialize ProtocolType
        override fun serialize(
            encoder: Encoder,
            value: PrismarineType
        ) {
            when(value) {
                is Simple -> encoder.encodeString(value.text)
                is Complex -> encoder.encodeSerializableValue(JsonObject.serializer(), value.obj)
                else -> TODO()
            }
        }
    }

    @Serializable
    class Simple(val text: String): PrismarineType()

    @Serializable
    class Complex(val obj: JsonObject): PrismarineType()

    @Serializable
    class Mapping(val type: String, val mappings: Map<String, String>): PrismarineType()

    @Serializable
    class Switch(val compareTo: String, val fields: PrismarineTypeMap, val default: PrismarineType? = null): PrismarineType()

    @Serializable
    class Container(val contained: List<NamedType>): PrismarineType()

    @Serializable
    class Buffer(val countType: String? = null, val count: Int? = null): PrismarineType()

    @Serializable
    class Array(val countType: String? = null, val type: PrismarineType, val count: String? = null): PrismarineType()

    @Serializable
    class Option(val type: PrismarineType): PrismarineType()

    @Serializable
    class BitFlags(val type: PrismarineType, val flags: List<String>): PrismarineType()

    @Serializable
    class BitFields(val fields: List<BitField>): PrismarineType()

    @Serializable
    class TopBitSetTerminatedArray(val type: PrismarineType): PrismarineType()

    @Serializable
    class RegistryEntryHolderSet(val base: NamedType, val otherwise: NamedType): PrismarineType()

    @Serializable
    class RegistryEntryHolder(val baseName: String, val otherwise: NamedType): PrismarineType()

    @Serializable
    class EntityMetadataLoop(val endVal: Int, val type: PrismarineType): PrismarineType()
}