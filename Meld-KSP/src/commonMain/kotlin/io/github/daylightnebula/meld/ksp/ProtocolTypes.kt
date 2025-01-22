package io.github.daylightnebula.meld.ksp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonArray

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
    val types: Map<String, JsonArray>
)
