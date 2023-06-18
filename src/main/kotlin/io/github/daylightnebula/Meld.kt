package io.github.daylightnebula

import io.github.daylightnebula.networking.bedrock.BedrockNetworkController
import io.github.daylightnebula.networking.java.JavaNetworkController

object Meld {
    // config TODO move to config file
    val javaPort = 25565
    val bedrockPort = 19132
    val version = "1.20.1"
    val protocol = 763
    val maxPlayers = 100
    val players = 0
    val description = "Hello World!"
    val favicon = "data:image/png;base64,<data>"
    val enforceSecureChat = false
    val previewsChat = false

}

// network controllers
val javaController = JavaNetworkController()
val bedrockController = BedrockNetworkController()

fun main() {
    println("Starting...")

    javaController.start()
    bedrockController.start()

    println("Started")
}
