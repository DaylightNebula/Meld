package io.github.daylightnebula.networking.java

import io.github.daylightnebula.Meld
import io.github.daylightnebula.networking.common.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.concurrent.thread

class JavaNetworkController: INetworkController {
    // setup socket
    lateinit var serverSocket: ServerSocket

    // acceptor that accepts incoming connections
    private val acceptor = thread(start = false) {
        while(true) {
            runBlocking {
                val connected = serverSocket.accept()
                javaConnections[connected.remoteAddress] = JavaConnection(connected, ChannelReader(connected.openReadChannel()), connected.openWriteChannel(autoFlush = true))
                println("New connection from ${connected.remoteAddress}")
            }
        }
    }

    // thread that listens for active java connections
    @OptIn(DelicateCoroutinesApi::class)
    val listener = thread(start = false) {
        while(true) {
            // for each connection, process incoming packets
            javaConnections.forEach { (addr, connection) ->
                val read = connection.read
                // skip if nothing new
                if (read.channel.availableForRead == 0) return@forEach

                // asynchronously read packet
                runBlocking {
                    val length = read.readVarInt()
                    val packetID = read.readVarInt()
                    val data = ByteArrayReader(read.readArray(length - 1))
                    println("Got packet $packetID with length $length on state ${connection.state}")

                    readPreJoinPacket(addr, connection, data, packetID)
                }
            }

            // slow everything down
            Thread.sleep(10)
        }
    }

    private fun pingJson(): JSONObject = JSONObject()
        .put("version", JSONObject().put("name", Meld.javaVersion).put("protocol", Meld.javaProtocol))
        .put("players", JSONObject().put("max", Meld.maxPlayers).put("online", Meld.players).put("sample", JSONArray().put(JSONObject().put("name", "hello_world").put("id", UUID.randomUUID().toString()))))
        .put("description", JSONObject().put("text", Meld.description))
        .put("favicon", JSONObject().put("favicon", Meld.favicon))
        .put("enforcesSecureChat", Meld.enforceSecureChat)
        .put("previewsChat", Meld.previewsChat)

    private suspend fun readPreJoinPacket(address: SocketAddress, connection: JavaConnection, reader: ByteArrayReader, packetID: Int) {
        when (connection.state) {
            JavaConnectionState.HANDSHAKE -> {
                // handle handshake packet
                val protocol = reader.readVarInt()
                val address = reader.readVarString()
                val port = reader.readUShort() // big endian
                val nextState = reader.readVarInt()

                // TODO broadcast event here

                // TODO only run this if the event passes
                when (nextState) {
                    1 -> connection.state = JavaConnectionState.STATUS
                    2 -> connection.state = JavaConnectionState.LOGIN
                    else -> throw IllegalArgumentException("Unknown handshake next state $nextState")
                }
            }

            JavaConnectionState.STATUS -> {
                when (packetID) {
                    0 -> {
                        // assemble status response packet
                        val packet = ByteWriter(0, DataPacketMode.JAVA)
                        packet.writeJSON(pingJson())

                        // write packet to connection
                        val bytes = packet.getData()
                        connection.write.writeFully(bytes, 0, bytes.size)
                    }

                    1 -> {
                        // read number and build a packet to report it back
                        val number = reader.readLong()
                        val packet = ByteWriter(1, DataPacketMode.JAVA)
                        packet.writeLong(number)

                        // write packet to connection
                        val bytes = packet.getData()
                        connection.write.writeFully(bytes, 0, bytes.size)

                        // remove pre join connection as it is not needed anymore
                        javaConnections.remove(address)
                            ?: throw RuntimeException("Java Pre Connection status removal failed")
                    }

                    else -> println("Unknown status packet ID $packetID")
                }
            }

            JavaConnectionState.LOGIN -> {
                // TODO Encryption
                when (packetID) {
                    0 -> {
                        // unpack packet
                        val name = reader.readVarString()
                        val hasUUID = reader.readBoolean()
                        val uuid: UUID = if (hasUUID) reader.readUUID() else UUID.randomUUID()

                        // build response packet
                        val packet = ByteWriter(2, DataPacketMode.JAVA)
                        packet.writeLong(uuid.mostSignificantBits)
                        packet.writeLong(uuid.leastSignificantBits)
                        packet.writeString(name)
                        packet.writeVarInt(0)

                        // write packet to connection
                        val bytes = packet.getData()
                        connection.write.writeFully(bytes, 0, bytes.size)

                        // TODO add "logged in" connection reference here
                        // TODO broadcast pre login complete event here
                        // https://wiki.vg/Protocol#Login_.28play.29
                        println("TODO java logged in")
                    }
                    else -> println("Unknown login packet ID $packetID")
                }
            }

            JavaConnectionState.IN_GAME -> TODO()
        }
    }

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
        javaConnections.forEach { it.value.socket.dispose() }
        serverSocket.dispose()
    }

    // connections
    private val javaConnections = hashMapOf<SocketAddress, JavaConnection>()
}