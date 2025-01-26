package io.github.daylightnebula.meld.server.networking.java

import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.PacketManager
import io.github.daylightnebula.meld.server.VarIntCodec
import io.github.daylightnebula.meld.server.networking.common.ByteArrayReader
import io.github.daylightnebula.meld.server.networking.common.ChannelReader
import io.github.daylightnebula.meld.server.networking.common.INetworkController
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlinx.serialization.json.JsonObject

object JavaNetworkController: INetworkController {
    // setup socket
    private lateinit var serverSocket: ServerSocket
    private val serverReady: Boolean
        get() = this::serverSocket.isInitialized

    // acceptor that accepts incoming connections
    @OptIn(DelicateCoroutinesApi::class)
    private val acceptor = GlobalScope.launch {
        while(!serverReady) { delay(100) }
        while(true) {
            runBlocking {
                val connected = serverSocket.accept()
                Meld.connections.add(JavaConnection(connected, ChannelReader(connected.openReadChannel()), connected.openWriteChannel(autoFlush = true)))
//                println("New connection from ${connected.remoteAddress}")
            }
        }
    }

    // thread that listens for active java connections
    @OptIn(DelicateCoroutinesApi::class)
    private val listener = GlobalScope.launch {
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
                    val length = VarIntCodec.decode(read)
                    val packetID = VarIntCodec.decode(read)

                    // try catch due to packet 122 in status state
                    val data = ByteArrayReader(read.readMany(length - 1))

                    PacketManager.handleJavaPacket(connection, packetID, data)
                }
            }

            // slow everything down
            delay(10)
        }
    }

    fun pingJson() = Meld.json.decodeFromString<JsonObject>("""
        {
          "version": {
              "name": "${Meld.config.javaVersion}",
              "protocol": ${Meld.config.javaProtocol}
          },
          "players": {
            "max": ${Meld.config.maxPlayers},
            "online": ${Meld.config.players},
            "sample": []
          },
          "description": {
            "text": "${Meld.config.description}"
          },
          "favicon": "${Meld.config.favicon}",
          "enforcesSecureChat": "${Meld.config.enforceSecureChat}"
        }
    """.trimIndent())

    override fun start() {
        // start socket
        val selectorManager = SelectorManager()
        serverSocket = runBlocking { aSocket(selectorManager).tcp().bind(port = Meld.config.javaPort) }

        // start threads
        acceptor.start()
        listener.start()

        println("Started java network controller")
    }

    override fun stop() {
        // stop threads
        acceptor.cancel()
        listener.cancel()

        // stop sockets
        Meld.connections.filter { it is JavaConnection }.forEach { (it as JavaConnection).socket.dispose() }
        serverSocket.dispose()
    }
}