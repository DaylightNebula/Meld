package io.github.daylightnebula.meld.ksp

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.jsonPrimitive
import java.io.Serial

@Serializable
data class ProtocolFile(
//    val types: Map<String, Any>,
    val handshaking: SCPacketContainer,
    val status: SCPacketContainer,
    val login: SCPacketContainer,
    val configuration: SCPacketContainer,
    val play: SCPacketContainer,
)

@Serializable
data class SCPacketContainer(
    val toClient: PacketTypes,
    val toServer: PacketTypes
)

@Serializable
data class PacketTypes(
    val types: Map<String, ProtocolType>
)

@Serializable
data class NamedType(
    val name: String? = null,
    val default: ProtocolType? = null,
    val type: ProtocolType,
    val anon: Boolean = false,
)

@Serializable(with = ProtocolType.Companion::class)
abstract class ProtocolType {
    companion object: KSerializer<ProtocolType> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ProtocolType")

        // Deserialize ProtocolType
        override fun deserialize(decoder: Decoder): ProtocolType {
            val input = decoder as? kotlinx.serialization.json.JsonDecoder
                ?: throw IllegalStateException("This serializer only works with JSON.")
            val element: JsonElement = input.decodeJsonElement()

            return when(element) {
                is JsonPrimitive -> Simple(element.content)
                is JsonObject -> Complex(element)
                is JsonArray -> when(element[0].jsonPrimitive.content) {
                    "mapper", "mapping" -> Json.decodeFromJsonElement<Mapping>(element[1])
                    "switch" -> Json.decodeFromJsonElement<CompareTo>(element[1])
                    "container" -> Container(Json.decodeFromJsonElement<List<NamedType>>(element[1]))
                    "buffer" -> Json.decodeFromJsonElement<Buffer>(element[1])
                    "array" -> Json.decodeFromJsonElement<Array>(element[1])
                    "option" -> Option(Json.decodeFromJsonElement(element[1]))
                    "bitflags" -> Json.decodeFromJsonElement<BitFlags>(element[1])
                    "bitfield" -> BitFields(Json.decodeFromJsonElement<List<BitField>>(element[1]))
                    "topBitSetTerminatedArray" -> Json.decodeFromJsonElement<TopBitSetTerminatedArray>(element[1])
                    else -> throw IllegalStateException("ProtocolType Array could not be decoded! ${element[0].jsonPrimitive.content}")
                }
            }
        }

        // Serialize ProtocolType
        override fun serialize(
            encoder: Encoder,
            value: ProtocolType
        ) {
            when(value) {
                is Simple -> encoder.encodeString(value.text)
                is Complex -> encoder.encodeSerializableValue(JsonObject.serializer(), value.obj)
                else -> TODO()
            }
        }
    }

    @Serializable
    class Simple(val text: String): ProtocolType()

    @Serializable
    class Complex(val obj: JsonObject): ProtocolType()

    @Serializable
    class Mapping(val type: String, val mappings: Map<String, String>): ProtocolType()

    @Serializable
    class CompareTo(val compareTo: String, val fields: Map<String, ProtocolType>, val default: ProtocolType? = null): ProtocolType()

    @Serializable
    class Container(val contained: List<NamedType>): ProtocolType()

    @Serializable
    class Buffer(val countType: String? = null, val count: Int? = null): ProtocolType()

    @Serializable
    class Array(val countType: String? = null, val type: ProtocolType, val count: String? = null): ProtocolType()

    @Serializable
    class Option(val type: ProtocolType): ProtocolType()

    @Serializable
    class BitFlags(val type: ProtocolType, val flags: List<String>): ProtocolType()

    @Serializable
    class BitFields(val fields: List<BitField>): ProtocolType()

    @Serializable
    class TopBitSetTerminatedArray(val type: ProtocolType): ProtocolType()
}

@Serializable
class BitField(val name: String, val size: Int, val signed: Boolean)
