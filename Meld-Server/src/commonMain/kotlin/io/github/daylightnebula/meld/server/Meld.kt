package io.github.daylightnebula.meld.server

import io.github.daylightnebula.meld.ksp.data.BuildJavaPackets
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.modules.MeldModule
import io.github.daylightnebula.meld.server.modules.ModuleLoader
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.benwoodworth.knbt.*
import okio.Buffer
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import kotlin.random.Random
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@BuildJavaPackets

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
) {
    @Transient val connections = mutableListOf<IConnection<*>>()
}

// handle config
val meldConfigFile = "config.json".toPath()
val configSerializer = Json {
    encodeDefaults = true
    prettyPrint = true
}

// config
val Meld = MeldConfig() // todo load config.json
//    if (FileSystem.SYSTEM.exists(meldConfigFile)) {
//        println("Exists ${FileSystem.SYSTEM.exists(meldConfigFile)}")
//        val text = FileSystem.SYSTEM.read("/config.json".toPath()) { readUtf8() }
//        configSerializer.decodeFromString<MeldConfig>(text)
//    } else MeldConfig()

val meldJson = Json {
    prettyPrint = false
    encodeDefaults = true
}

val meldNbt = Nbt {
    variant = NbtVariant.JavaNetwork(764)
    compression = NbtCompression.None
}

// needs bedrock function
fun NeedsBedrock(): Nothing = throw NotImplementedError("This function requires a bedrock implementation!")

// send keep alive packet to all in game java connections once a second
@OptIn(DelicateCoroutinesApi::class)
val keepAliveThread = GlobalScope.launch {
    while (true) {
        delay(1000)
        try {
            Meld.connections
                .forEach {
                    if (it is JavaConnection) {
                        when (it.state) {
                            JavaConnectionState.HANDSHAKE -> {}
                            JavaConnectionState.STATUS -> {}
                            JavaConnectionState.LOGIN -> {}
                            JavaConnectionState.CONFIG -> {} //it.sendPacket(JavaClientConfigKeepAlive(Random.nextLong()))
                            JavaConnectionState.IN_GAME -> it.sendPacket(JavaClientPlayKeepAlive(Random.nextLong()))
                        }
                    }
                }
        } catch (_: ConcurrentModificationException) {}
    }
}

fun runMeldServer(vararg modules: MeldModule) {
    println("Updating config...")
    FileSystem.SYSTEM.write(meldConfigFile) { writeUtf8(configSerializer.encodeToString(Meld)) }

    println("Loading modules...")
    ModuleLoader.load(modules)

    println("Starting...")
    JavaNetworkController.start()

    println("Started")

    runBlocking { keepAliveThread.join() }
}

@OptIn(ExperimentalUuidApi::class)
data class ConnectionAbortedEvent(val connection: IConnection<*>): Event {
    companion object: Event.Data<ConnectionAbortedEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(ConnectionAbortedEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

fun Any.encodeToNbt(): NbtCompound = meldNbt.encodeToNbtTag(this) as NbtCompound
