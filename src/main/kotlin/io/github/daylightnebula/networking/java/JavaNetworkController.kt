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
                val protocol = reader.readVarInt()
                val address = reader.readVarString()
                val port = reader.readUShort() // big endian
                val nextState = reader.readVarInt()

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
                        val packet = DataPacket(0, DataPacketMode.JAVA)
                        packet.writeJSON(pingJson())

                        // write packet to connection
                        val bytes = packet.getData()
                        connection.write.writeFully(bytes, 0, bytes.size)
                    }

                    1 -> {
                        // read number and build a packet to report it back
                        val number = reader.readLong()
                        val packet = DataPacket(1, DataPacketMode.JAVA)
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

            JavaPreConnectionState.LOGIN -> {
                // TODO Encryption
                when (packetID) {
                    0 -> {
                        // unpack packet
                        val name = reader.readVarString()
                        val hasUUID = reader.readBoolean()
                        val uuid: UUID = if (hasUUID) reader.readUUID() else UUID.randomUUID()

                        // build response packet
                        val packet = DataPacket(2, DataPacketMode.JAVA)
                        packet.writeLong(uuid.mostSignificantBits)
                        packet.writeLong(uuid.leastSignificantBits)
                        packet.writeString(name)
                        packet.writeVarInt(0)

                        // write packet to connection
                        val bytes = packet.getData()
                        connection.write.writeFully(bytes, 0, bytes.size)

                        // remove pre join connection
                        javaPreConnections.remove(address)
                            ?: throw RuntimeException("Java Pre Connection status removal failed in login state")
                    }
                    else -> println("Unknown login packet ID $packetID")
                }
            }

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

        println("Started java network controller")
    }

    override fun stop() {
        // stop threads
        acceptor.join(100)
        listener.join(100)

        // stop sockets
        javaPreConnections.forEach { it.value.socket.dispose() }
        serverSocket.dispose()
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