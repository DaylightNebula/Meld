package io.github.daylightnebula.networking.java

import io.github.daylightnebula.Meld
import io.github.daylightnebula.Meld.connections
import io.github.daylightnebula.PacketHandler
import io.github.daylightnebula.networking.common.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.concurrent.thread

object JavaNetworkController: INetworkController {
    // setup socket
    private lateinit var serverSocket: ServerSocket

    // acceptor that accepts incoming connections
    private val acceptor = thread(start = false) {
        while(true) {
            runBlocking {
                val connected = serverSocket.accept()
                connections.add(JavaConnection(connected, ChannelReader(connected.openReadChannel()), connected.openWriteChannel(autoFlush = true)))
                println("New connection from ${connected.remoteAddress}")
            }
        }
    }

    // thread that listens for active java connections
    @OptIn(DelicateCoroutinesApi::class)
    val listener = thread(start = false) {
        while(true) {
            // for each connection, process incoming packets
            connections.filter { it is JavaConnection }.forEach { connection ->
                connection as JavaConnection
                val read = connection.read
                // skip if nothing new
                if (read.channel.availableForRead == 0) return@forEach

                // asynchronously read packet
                runBlocking {
                    val length = read.readVarInt()
                    val packetID = read.readVarInt()
//                    println("Got packet $packetID with length $length on state ${connection.state}")

                    // try catch due to packet 122 in status state
                    val data = ByteArrayReader(read.readArray(length - 1))

                    PacketHandler.handleJavaPacket(connection, packetID, data)
                }
            }

            // slow everything down
            Thread.sleep(10)
        }
    }

    fun pingJson(): JSONObject = JSONObject()
        .put("version", JSONObject().put("name", Meld.javaVersion).put("protocol", Meld.javaProtocol))
        .put("players", JSONObject().put("max", Meld.maxPlayers).put("online", Meld.players).put("sample", JSONArray().put(JSONObject().put("name", "hello_world").put("id", UUID.randomUUID().toString()))))
        .put("description", JSONObject().put("text", Meld.description))
        .put("favicon", JSONObject().put("favicon", Meld.favicon))
        .put("enforcesSecureChat", Meld.enforceSecureChat)
        .put("previewsChat", Meld.previewsChat)

    override fun start() {
        // start socket
        val selectorManager = ActorSelectorManager(Dispatchers.IO)
        serverSocket = aSocket(selectorManager).tcp().bind(port = Meld.javaPort)

        // start threads
        acceptor.start()
        listener.start()

        println("Started java network controller")
    }

    override fun stop() {
        // stop threads
        acceptor.join(100)
        listener.join(100)

        // stop sockets
        connections.filter { it is JavaConnection }.forEach { (it as JavaConnection).socket.dispose() }
        serverSocket.dispose()
    }
}