package io.github.daylightnebula.meld.server

import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.ModuleLoader
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockNetworkController
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaNetworkController
import io.github.daylightnebula.meld.server.registries.RegistryCodec
import kotlin.concurrent.thread

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
    val serverName = "Meld test server!"
    val description = "Hello World!"
    val favicon = "data:image/png;base64,<data>"
    val enforceSecureChat = false
    val previewsChat = false
    val viewDistance = 8
    val simDistance = 8
    val isFlatWorld = false
    val portalCooldown = 20

    // connections
    val connections = mutableListOf<IConnection<*>>()
}

fun main() {
    println("Loading modules...")
    ModuleLoader.load()

    println("Adding shutdown hook...")
    Runtime.getRuntime().addShutdownHook(thread(start = false) {
        println("Shutting down modules...")
        ModuleLoader.modules.forEach { it.onDisable() }
        println("Goodbye :-(")
    })

    println("Starting...")

    JavaNetworkController.start()

    println("Started")
}
