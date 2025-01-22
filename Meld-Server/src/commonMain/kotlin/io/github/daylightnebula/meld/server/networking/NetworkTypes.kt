package io.github.daylightnebula.meld.server.networking

import kotlinx.serialization.json.JsonObject

data class KnownPack(
    val namespace: String,
    val id: String,
    val version: String,
)

data class CustomReportDetail(
    val title: String,
    val description: String
)

data class ServerLink(
    val isBuiltIn: Boolean,
    val label: Int,
    val url: String
)

data class LoginEntry(
    val name: String,
    val value: String,
    val signature: String?
)

data class AwardStatsEntry(
    val categoryID: Int,
    val statisticID: Int,
    val value: Int
)

class CommandNode

data class ChunkBiomeData(
    val chunkZ: Int,
    val chunkX: Int,
    val data: Array<Byte>
)

data class CommandSuggestion(
    val match: String,
    val tooltip: JsonObject?
)
