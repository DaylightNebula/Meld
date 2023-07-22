package io.github.daylightnebula.meld.server

import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.modules.ModuleLoader
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaKeepAlivePacket
import io.github.daylightnebula.meld.server.networking.java.JavaNetworkController
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.lang.Thread.sleep
import java.util.ConcurrentModificationException
import kotlin.concurrent.thread

@Serializable
data class MeldConfig (
    // java specific
    val javaPort: Int = 25565,
    val javaProtocol: Int = 763,
    val javaVersion: String = "1.20.1",

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
) {
    @Transient val connections = mutableListOf<IConnection<*>>()
}

// handle config
val meldConfigFile = File("config.json")
val configSerializer = Json {
    encodeDefaults = true
    prettyPrint = true
}

// config
val Meld =
    if (meldConfigFile.exists()) configSerializer.decodeFromString<MeldConfig>(meldConfigFile.readText())
    else MeldConfig()

// needs bedrock function
fun NeedsBedrock(): Nothing = throw NotImplementedError("This function requires a bedrock implementation!")

// send keep alive packet to all in game java connections once a second
val keepAliveThread = thread {
    while (true) {
        sleep(1000)
        try {
            Meld.connections
                .forEach { if (it is JavaConnection) it.sendPacket(JavaKeepAlivePacket()) }
        } catch (_: ConcurrentModificationException) {}
    }
}

fun main() {
    println("Updating config...")
    meldConfigFile.writeText(configSerializer.encodeToString(Meld))

    println("Loading modules...")
    ModuleLoader.load()

    println("Adding shutdown hook...")
    Runtime.getRuntime().addShutdownHook(thread(start = false) {
        println("Shutting down modules...")
        ModuleLoader.modules.forEach { it.onDisable() }
        println("Shutting down ticker...")
        keepAliveThread.join()
        println("Goodbye :-(")
    })

    println("Starting...")

    JavaNetworkController.start()

    println("Started")
}

data class ConnectionAbortedEvent(val connection: IConnection<*>): Event