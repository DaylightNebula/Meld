package io.github.daylightnebula.meld.server

import io.github.daylightnebula.meld.ksp.data.BuildJavaPackets
import io.github.daylightnebula.meld.server.generated.JavaClientPlayKeepAlive
import io.github.daylightnebula.meld.server.modules.KeepAliveBundle
import io.github.daylightnebula.meld.server.modules.MeldModule
import io.github.daylightnebula.meld.server.modules.ModuleLoader
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaNetworkController
import kotlinx.coroutines.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import net.benwoodworth.knbt.Nbt
import net.benwoodworth.knbt.NbtCompression
import net.benwoodworth.knbt.NbtVariant
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM
import kotlin.random.Random

@BuildJavaPackets
object Meld {
    val connections = mutableListOf<IConnection<*>>()

    // handle config
    val meldConfigFile = "config.json".toPath()
    val configSerializer = Json {
        encodeDefaults = true
        prettyPrint = true
    }

    // config
    val config = MeldConfig() // todo load config.json
//    if (FileSystem.SYSTEM.exists(meldConfigFile)) {
//        println("Exists ${FileSystem.SYSTEM.exists(meldConfigFile)}")
//        val text = FileSystem.SYSTEM.read("/config.json".toPath()) { readUtf8() }
//        configSerializer.decodeFromString<MeldConfig>(text)
//    } else MeldConfig()

    val json = Json {
        prettyPrint = false
        encodeDefaults = true
    }

    val nbt = Nbt {
        variant = NbtVariant.JavaNetwork(764)
        compression = NbtCompression.None
    }

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
        FileSystem.SYSTEM.write(meldConfigFile) { writeUtf8(configSerializer.encodeToString(config)) }

        println("Loading modules...")
        PacketManager.register(KeepAliveBundle)
        ModuleLoader.load(modules)

        println("Starting...")
        JavaNetworkController.start()

        println("Started")

        runBlocking { keepAliveThread.join() }
    }

}
