package io.github.daylightnebula

import com.google.gson.Gson
import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.join.JoinEventListener
import io.github.daylightnebula.login.LoginBundle
import io.github.daylightnebula.networking.bedrock.BedrockNetworkController
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.networking.java.JavaNetworkController

object Meld {
    // config TODO move to config file
    // java specific
    val javaPort = 25577
    val javaProtocol = 763
    val javaVersion = "1.20.1"

    // bedrock specific
    val bedrockPort = 19133
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
    val viewDistance = 8
    val simDistance = 4
    val isFlatWorld = false
    val portalCooldown = 20

    // connections
    val connections = mutableListOf<IConnection<*>>()
}

fun main() {
    println("Registering event listeners")
    EventBus.register(JoinEventListener())

    println("Registering packet bundles...")
    PacketHandler.register(LoginBundle())

    println("Starting...")

    // start the network controllers
    JavaNetworkController.start()
    BedrockNetworkController.start()

    println("Started")
}
