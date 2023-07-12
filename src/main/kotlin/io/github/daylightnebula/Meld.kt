package io.github.daylightnebula

import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.inventories.InventoryBundle
import io.github.daylightnebula.player.PlayerListener
import io.github.daylightnebula.login.LoginBundle
import io.github.daylightnebula.networking.bedrock.BedrockNetworkController
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.networking.java.JavaNetworkController
import io.github.daylightnebula.player.PlayerBundle
import io.github.daylightnebula.registries.RegistryCodec
import io.github.daylightnebula.worlds.World

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
    println("NBT ${RegistryCodec.nbt}")
    println("Registering event listeners")
    EventBus.register(PlayerListener())

    println("Registering packet bundles...")
    PacketHandler.register(LoginBundle())
    PacketHandler.register(PlayerBundle())
    PacketHandler.register(InventoryBundle())

    println("Loading world...")
    World.init()

    println("Starting...")

    // start the network controllers
    JavaNetworkController.start()
//    BedrockNetworkController.start()

    println("Started")
}
