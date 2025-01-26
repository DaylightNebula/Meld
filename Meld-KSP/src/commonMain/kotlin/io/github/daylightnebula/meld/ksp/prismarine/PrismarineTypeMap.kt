package io.github.daylightnebula.meld.ksp.prismarine

import io.github.daylightnebula.meld.ksp.MeldProcessor
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.jsonObject

@Serializable(with = PrismarineTypeMap.Companion::class)
data class PrismarineTypeMap(
    val types: Map<String, PrismarineType>
) {
    companion object: KSerializer<PrismarineTypeMap> {
        override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ProtocolTypeMap")

        override fun serialize(
            encoder: Encoder,
            value: PrismarineTypeMap
        ) = throw IllegalStateException("No serializing")

        override fun deserialize(decoder: Decoder): PrismarineTypeMap {
            val input = decoder as? JsonDecoder
                ?: throw IllegalStateException("This serializer only works with JSON.")
            val element = input.decodeJsonElement().jsonObject

            return PrismarineTypeMap(
                types = element.entries.associate { (key, child) ->
                    if (MeldProcessor.codecs.containsKey(key))
                        return@associate key to PrismarineType.Simple(key)

                    key to Json.decodeFromJsonElement<PrismarineType>(child)
                }
            )
        }
    }
}
