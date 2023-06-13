package io.github.daylightnebula.networking.java

import io.github.daylightnebula.javaPort
import io.github.daylightnebula.networking.common.ByteArrayReader
import io.github.daylightnebula.networking.common.ChannelReader
import io.github.daylightnebula.networking.common.INetworkController
import io.github.daylightnebula.networking.common.NetworkUtils
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlin.concurrent.thread

class JavaNetworkController: INetworkController {
    // setup socket
    lateinit var serverSocket: ServerSocket

    // acceptor that accepts incoming connections
    private val acceptor = thread(start = false) {
        while(true) {
            runBlocking {
                val connected = serverSocket.accept()
                javaConnections[connected.remoteAddress] = JavaConnection(connected, ChannelReader(connected.openReadChannel()), connected.openWriteChannel())
                println("New connection from ${connected.remoteAddress}")
            }
        }
    }

    // thread that listens for active java connections
    @OptIn(DelicateCoroutinesApi::class)
    val listener = thread(start = false) {
        while(true) {
            // for each connection, process incoming packets
            javaConnections.forEach { addr, (socket, read, _, state) ->
                // skip if nothing new
                if (read.channel.availableForRead == 0) return@forEach

                // asynchronously read packet
                GlobalScope.async {
                    val length = NetworkUtils.readVarInt(read)
                    val packetID = NetworkUtils.readVarInt(read)
                    val data = ByteArrayReader(read.readArray(length))
                    println("Got packet $packetID with length $length")

                    when (state) {
                        JavaConnectionState.HANDSHAKE -> {
                            val protocol = NetworkUtils.readVarInt(data)
                            println("Found protocol: $protocol")
                        }
                        JavaConnectionState.STATUS -> TODO()
                        JavaConnectionState.LOGIN -> TODO()
                        JavaConnectionState.IN_GAME -> TODO()
                    }
                }
            }

            // slow everything down
            Thread.sleep(10)
        }
    }

    override fun start() {
        // start socket
        val selectorManager = ActorSelectorManager(Dispatchers.IO)
        serverSocket = aSocket(selectorManager).tcp().bind(port = javaPort)

        // start threads
        acceptor.start()
        listener.start()
    }

    override fun stop() {
        // stop threads
        acceptor.join(100)
        listener.join(100)
    }

    // connections
    private val javaConnections = hashMapOf<SocketAddress, JavaConnection>()
    data class JavaConnection(val socket: ASocket, val read: ChannelReader, val write: ByteWriteChannel, val state: JavaConnectionState = JavaConnectionState.HANDSHAKE)

    // connection state
    enum class JavaConnectionState {
        HANDSHAKE,
        STATUS,
        LOGIN,
        IN_GAME
    }
}