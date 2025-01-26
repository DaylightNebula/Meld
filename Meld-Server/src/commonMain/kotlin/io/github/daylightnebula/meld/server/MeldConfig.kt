package io.github.daylightnebula.meld.server

import kotlinx.serialization.Serializable

@Serializable
data class MeldConfig (
    // java specific
    val javaPort: Int = 25565,
    val javaProtocol: Int = 769,
    val javaVersion: String = "1.21.4",

    // bedrock specific
    val bedrockPort: Int = 19132,
    val bedrockPortv6: Int = 19133,
    val raknetProtocol: Int = 11,
    val bedrockProtocol: Int = 593,
    val bedrockVersion: String = "1.20.10",

    // other stuffs
    val maxPlayers: Int = 100,
    val players: Int = 0,
    val serverName: String = "Meld test server!",
    val description: String = "Hello World!",
    val favicon: String = "data:image/png;base64,<data>",
    val enforceSecureChat: Boolean = false,
    val previewsChat: Boolean = false,
    val viewDistance: Int = 8,
    val simDistance: Int = 8,
    val isFlatWorld: Boolean = false,
    val portalCooldown: Int = 20,
)