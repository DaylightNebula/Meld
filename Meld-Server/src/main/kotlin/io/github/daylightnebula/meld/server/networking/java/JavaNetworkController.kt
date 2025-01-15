package io.github.daylightnebula.meld.server.networking.java

import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.PacketManager
import io.github.daylightnebula.meld.server.meldJson
import io.github.daylightnebula.meld.server.networking.common.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.JsonObject
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
                Meld.connections.add(JavaConnection(connected, ChannelReader(connected.openReadChannel()), connected.openWriteChannel(autoFlush = true)))
                println("New connection from ${connected.remoteAddress}")
            }
        }
    }

    // thread that listens for active java connections
    @OptIn(DelicateCoroutinesApi::class)
    val listener = thread(start = false) {
        while(true) {
            // for each connection, process incoming packets
            Meld.connections.filter { it is JavaConnection }.forEach { connection ->
                // connection is java connection and get read channel
                connection as JavaConnection
                val read = connection.read

                // skip if nothing new
                if (read.channel.availableForRead == 0) return@forEach

                // asynchronously read packet
                runBlocking {
                    val length = read.readVarInt()
                    val packetID = read.readVarInt()
                    println("Received $packetID")
//                    println("Got packet $packetID with length $length on state ${connection.state}")

                    // try catch due to packet 122 in status state
                    val data = ByteArrayReader(read.readArray(length - 1))

                    PacketManager.handleJavaPacket(connection, packetID, data)
                }
            }

            // slow everything down
            Thread.sleep(10)
        }
    }

    fun pingJson(): JsonObject = meldJson.decodeFromString("""
        {
          "version": {
              "name": ${Meld.javaVersion},
              "protocol": ${Meld.javaProtocol}
          },
          "players": {
            "max": ${Meld.maxPlayers},
            "online": ${Meld.players},
            "sample": []
          },
          "description": {
            "text": ${Meld.description}
          },
          "favicon": ${Meld.favicon},
          "enforcesSecureChat": ${Meld.enforceSecureChat}
        }
    """.trimIndent())

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
        Meld.connections.filter { it is JavaConnection }.forEach { (it as JavaConnection).socket.dispose() }
        serverSocket.dispose()
    }
}