package io.github.daylightnebula

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.experimental.and

// config TODO move to config file
val javaPort = 25565
val bedrockPort = 19132

// create primary tcp socket
val selectorManager = ActorSelectorManager(Dispatchers.IO)
val javaSocket = aSocket(selectorManager).tcp().bind(port = javaPort)

// connections
val connections = hashMapOf<SocketAddress, Connection>()
data class Connection(val socket: ASocket, val read: ByteReadChannel, val write: ByteWriteChannel)

// acceptors
//fun emptyAcceptor(socket: Acceptable<Socket>, onConnection: (socket: Socket) -> Unit) = thread {
//    while(true) { runBlocking {
//        val connected = socket.accept()
//        onConnection(connected)
//    }}
//}
//val javaAcceptor = emptyAcceptor(javaSocket) { socket ->
//    println("New connection from ${socket.remoteAddress}")
//}

val javaAcceptor = thread {
    while(true) {
        runBlocking {
            val connected = javaSocket.accept()
            connections[connected.remoteAddress] = Connection(connected, connected.openReadChannel(), connected.openWriteChannel())
            println("New connection from ${connected.remoteAddress}")
        }
    }
}

const val SEGMENT_BITS = 0x7F
const val CONTINUE_BIT = 0x80
suspend fun readVarInt(read: ByteReadChannel): Int {
    var value = 0
    var position = 0
    var currentByte: Int
    while (true) {
        currentByte = read.readByte().toInt()
        value = value or (currentByte and SEGMENT_BITS shl position)
        if (currentByte and CONTINUE_BIT === 0) break
        position += 7
        if (position >= 32) throw RuntimeException("VarInt is too big")
    }
    return value
}

@OptIn(DelicateCoroutinesApi::class)
val javaListener = thread {
    while(true) {
        // for each connection, process incoming packets
        connections.forEach { addr, (socket, read, _) ->
            // skip if nothing new
            if (read.availableForRead == 0) return@forEach

            GlobalScope.async {
                val length = readVarInt(read)
                val packetID = readVarInt(read)
                val data = ByteArray(length)
                read.readFully(data, 0, length)
                println("Got packet $packetID with length $length")
            }
        }

        // slow everything down
        sleep(10)
    }
}

fun main() {
    println("Starting...")

    println("Started")
}
