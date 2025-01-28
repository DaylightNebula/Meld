package io.github.daylightnebula.meld.ksp.prismarine

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProtocolFile(
    val types: PrismarineTypeMap,
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
    val types: PrismarineTypeMap
)

@Serializable
data class NamedType(
    val name: String? = null,
    val default: PrismarineType? = null,
    val type: PrismarineType,
    val anon: Boolean = false,
)

@Serializable
class BitField(val name: String, val size: Int, val signed: Boolean)

@Serializable
data class PrismarineBiome(
    val id: Int,
    val name: String,
    val category: String,
    val temperature: Float,
    @SerialName("has_precipitation")
    val hasPrecipitation: Boolean,
    val dimension: String,
    val displayName: String,
    val color: Int
)

@Serializable
data class PrismarineEntity(
    val id: Int,
    val internalId: Int,
    val name: String,
    val displayName: String,
    val width: Float,
    val height: Float,
    val type: String,
    val category: String,
    val metadataKeys: List<String>
)
