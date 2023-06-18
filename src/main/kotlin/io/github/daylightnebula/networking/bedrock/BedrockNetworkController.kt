package io.github.daylightnebula.networking.bedrock

import io.github.daylightnebula.Meld
import io.github.daylightnebula.networking.common.ByteReadPacketReader
import io.github.daylightnebula.networking.common.INetworkController
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread

class BedrockNetworkController: INetworkController {

    // socket
    private lateinit var serverSocket: BoundDatagramSocket
    private lateinit var readChannel: ByteReadChannel

    // thread that listens for packets
    val listener = thread(start = false) {
        // start infinite read loop
        while(true) {
            runBlocking {
                // read packet and its headers
                val received = serverSocket.receive()
                val reader = ByteReadPacketReader(received.packet)
                val packetID = reader.nextByte()

                println("Got bedrock packet $packetID")
            }

            // slow everything down
//            Thread.sleep(10)
        }
    }

    override fun start() {
        // start socket
        val selectorManager = ActorSelectorManager(Dispatchers.IO)
        serverSocket = aSocket(selectorManager).udp().bind(InetSocketAddress("127.0.0.1", Meld.bedrockPort))

        // open channels
        readChannel = serverSocket.openReadChannel()

        // start threads
        listener.start()

        println("Started bedrock network controller")
    }

    override fun stop() {
        // stop threads
        listener.join(100)

        // stop sockets
        serverSocket.dispose()
    }
}