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
                javaPreConnections[connected.remoteAddress] = JavaPreConnection(connected, ChannelReader(connected.openReadChannel()), connected.openWriteChannel(autoFlush = true))
                println("New connection from ${connected.remoteAddress}")
            }
        }
    }

    // thread that listens for active java connections
    @OptIn(DelicateCoroutinesApi::class)
    val listener = thread(start = false) {
        while(true) {
            // for each connection, process incoming packets
            javaPreConnections.forEach { (addr, connection) ->
                val read = connection.read
                // skip if nothing new
                if (read.channel.availableForRead == 0) return@forEach

                // asynchronously read packet
                runBlocking {
                    val length = NetworkUtils.readVarInt(read)
                    val packetID = NetworkUtils.readVarInt(read)
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
        .put("version", JSONObject().put("name", Meld.version).put("protocol", Meld.protocol))
        .put("players", JSONObject().put("max", Meld.maxPlayers).put("online", Meld.players).put("sample", JSONArray().put(JSONObject().put("name", "hello_world").put("id", UUID.randomUUID().toString()))))
        .put("description", JSONObject().put("text", Meld.description))
        .put("favicon", JSONObject().put("favicon", Meld.favicon))
        .put("enforcesSecureChat", Meld.enforceSecureChat)
        .put("previewsChat", Meld.previewsChat)

    private suspend fun readPreJoinPacket(address: SocketAddress, connection: JavaPreConnection, reader: ByteArrayReader, packetID: Int) {
        when (connection.state) {
            JavaPreConnectionState.HANDSHAKE -> {
                // handle handshake packet
                val protocol = NetworkUtils.readVarInt(reader)
                val address = NetworkUtils.readVarString(reader)
                val port = NetworkUtils.readUShort(reader, true)
                val nextState = NetworkUtils.readVarInt(reader)

                // TODO broadcast event here

                // TODO only run this if the event passes
                when (nextState) {
                    1 -> connection.state = JavaPreConnectionState.STATUS
                    2 -> connection.state = JavaPreConnectionState.LOGIN
                    else -> throw IllegalArgumentException("Unknown handshake next state $nextState")
                }
            }

            JavaPreConnectionState.STATUS -> {
                when (packetID) {
                    0 -> {
                        // assemble status response packet
                        val packet = DataPacket(0)
                        packet.writeJSON(pingJson())

                        // write packet to connection
                        val bytes = packet.getData()
                        connection.write.writeFully(bytes, 0, bytes.size)
                    }

                    1 -> {
                        // read number and build a packet to report it back
                        val number = NetworkUtils.readLong(reader)
                        val packet = DataPacket(1)
                        packet.writeLong(number)

                        // write packet to connection
                        val bytes = packet.getData()
                        connection.write.writeFully(bytes, 0, bytes.size)

                        // remove pre join connection as it is not needed anymore
                        javaPreConnections.remove(address)
                            ?: throw RuntimeException("Java Pre Connection status removal failed")
                    }

                    else -> println("Unknown status packet ID $packetID")
                }
            }

            JavaPreConnectionState.LOGIN -> TODO()
            JavaPreConnectionState.IN_GAME -> TODO()
        }
    }

    override fun start() {
        // start socket
        val selectorManager = ActorSelectorManager(Dispatchers.IO)
        serverSocket = aSocket(selectorManager).tcp().bind(port = Meld.javaPort)

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
    private val javaPreConnections = hashMapOf<SocketAddress, JavaPreConnection>()
    data class JavaPreConnection(val socket: ASocket, val read: ChannelReader, val write: ByteWriteChannel, var state: JavaPreConnectionState = JavaPreConnectionState.HANDSHAKE)

    // connection state
    enum class JavaPreConnectionState {
        HANDSHAKE,
        STATUS,
        LOGIN,
        IN_GAME
    }
}