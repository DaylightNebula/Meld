package io.github.daylightnebula

import io.github.daylightnebula.login.LoginBundle
import io.github.daylightnebula.networking.bedrock.BedrockNetworkController
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.networking.java.JavaNetworkController
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm
import org.cloudburstmc.protocol.bedrock.packet.*

object Meld {
    // config TODO move to config file
    // java specific
    val javaPort = 25565
    val javaProtocol = 763
    val javaVersion = "1.20.1"

    // bedrock specific
    val bedrockPort = 19132
    val bedrockPortv6 = 19133
    val reknetProtcol = 11
    val bedrockProtocol = 593
    val bedrockVersion = "1.20.10"

    // other stuffs
    val maxPlayers = 100
    val players = 0
    val description = "Hello World!"
    val favicon = "data:image/png;base64,<data>"
    val enforceSecureChat = false
    val previewsChat = false

    // connections
    val connections = mutableListOf<IConnection<*>>()
}

fun main() {
    println("Registering packet bundles...")
    PacketHandler.registerBundle(LoginBundle())

    println("Starting...")

    // start the network controllers
    JavaNetworkController.start()
    BedrockNetworkController.start()

    println("Started")
}
