package io.github.daylightnebula

import io.github.daylightnebula.networking.java.JavaNetworkController

// config TODO move to config file
val javaPort = 25565
val bedrockPort = 19132

// network controllers
val javaController = JavaNetworkController()

fun main() {
    println("Starting...")

    javaController.start()

    println("Started")
}
