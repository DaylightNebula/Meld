package io.github.daylightnebula.networking.bedrock

import io.github.daylightnebula.Meld
import io.github.daylightnebula.networking.common.ByteReadPacketReader
import io.github.daylightnebula.networking.common.DataPacket
import io.github.daylightnebula.networking.common.DataPacketMode
import io.github.daylightnebula.networking.common.INetworkController
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.concurrent.thread

class BedrockNetworkController: INetworkController {

    // socket
    private val serverUUID: UUID = UUID.randomUUID()
    private lateinit var serverSocket: BoundDatagramSocket

    // thread that listens for packets
    val listener = thread(start = false) {
        // start infinite read loop
        while(true) {
            runBlocking {
                // receive packet and check if there is a connection for that address
                val received = serverSocket.receive()
                val connection = preConnections[received.address]

                // handle packet based on if it is a connected or unconnected address
                if (connection != null) handleConnected(received)
                else handleNonConnected(received)
            }
        }
    }

    suspend fun handleConnected(received: Datagram) {
        val reader = ByteReadPacketReader(received.packet)
//        println("Read: ${reader.readArray(reader.packet.remaining.toInt()).map { it.toUInt().toString(16).padStart(2, '0') }.joinToString(" ")}")
        println("Reading: ${reader.packet.remaining}") // HOLY SHIT THAT WORKED
        reader.readArray(1)                     // TURNS OUT THIS IS A PACKET ID AND KOTLIN IS JUST DUMB
        val sequenceID = reader.read3Int()
        val flags = reader.nextByte()
        val length = reader.readUShort()
        val index = reader.read3Int()
        val packetID = reader.nextByte()
        println("Sequence ID $sequenceID $flags $length $index ${packetID.toUInt().toString(16)}")
    }

    suspend fun handleNonConnected(received: Datagram) {
        // read packet and its header
        val reader = ByteReadPacketReader(received.packet)
        val packetID = reader.nextByte()

        when(packetID.toInt()) {
            // ping packet
            0x00 -> {
                val time = reader.readLong()
                val packet = DataPacket(0x03, DataPacketMode.BEDROCK)
                packet.writeLong(time)
                packet.writeLong(System.currentTimeMillis())
                serverSocket.send(Datagram(ByteReadPacket(packet.getData()), received.address))
            }

            // unconnected ping
            0x01, 0x02 -> {
                // create packet response
                val dataPacket = DataPacket(0x1c, DataPacketMode.BEDROCK)
                dataPacket.writeLong(System.currentTimeMillis())    // 8 bytes
                dataPacket.writeLong(serverUUID.mostSignificantBits) // 8 bytes
                dataPacket.writeByteArray(BedrockMagic.bytes)   // 16 bytes
                dataPacket.writeString("MCPE;${Meld.description};${Meld.bedrockProtocol};${Meld.bedrockVersion};${Meld.players};${Meld.maxPlayers};13253860892328930865;${Meld.description};Survival;1;${Meld.bedrockPort};${Meld.bedrockPortv6};") // 14 bytes

                // send back packet
                serverSocket.send(Datagram(ByteReadPacket(dataPacket.getData()), received.address))
            }

            // open connection request
            0x05 -> {
                // unpack
                val magic = reader.readArray(16)
                val protocol = reader.nextByte().toInt()
                val padding = reader.packet.remaining // AKA MTU in protocol documentation

                // verify magic and protocol
                if (BedrockMagic.verify(magic) && Meld.reknetProtcol == protocol) {
                    // send open conn 1 reply
                    val packet = DataPacket(0x06, DataPacketMode.BEDROCK)
                    packet.writeByteArray(BedrockMagic.bytes)
                    packet.writeLong(serverUUID.mostSignificantBits)
                    packet.writeBoolean(false)
                    packet.writeShort(padding.toShort())
                    serverSocket.send(Datagram(ByteReadPacket(packet.getData()), received.address))

                    println("New connection with protocol $protocol")
                } else
                    println("Open connection request with invalid magic or protocol version (request protocol $protocol)")
            }

            // open connection request 2
            0x07 -> {
                // unpack
                val magic = reader.readArray(16)
                val address = reader.readArray(7)
                val mtu = reader.readUShort() // maybe normal short and not unsigned
                val clientGUID = reader.readLong()

                // verify input magic
                if (BedrockMagic.verify(magic)) {
                    // respond open connection reply 2
                    val packet = DataPacket(0x08, DataPacketMode.BEDROCK)
                    packet.writeByteArray(BedrockMagic.bytes)
                    packet.writeLong(serverUUID.mostSignificantBits)
                    packet.writeByteArray(address)
                    packet.writeUShort(mtu)
                    packet.writeBoolean(false)
                    serverSocket.send(Datagram(ByteReadPacket(packet.getData()), received.address))

                    // save connection
                    preConnections[received.address] = BedrockPreConnection(received.address, BedrockState.OPEN)
                } else
                    println("Open connection request 2 with invalid magic")
            }

            // otherwise, unknown packet
            else -> println("WARN Unknown Reknet unconnected packet of ID ${packetID.toInt().toString(16).padStart(2, '0')} with length ${received.packet.remaining}")
        }
    }

    override fun start() {
        // start socket
        val selectorManager = ActorSelectorManager(Dispatchers.IO)
        serverSocket = aSocket(selectorManager).udp().bind(InetSocketAddress("0.0.0.0", Meld.bedrockPort))

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

    val preConnections = hashMapOf<SocketAddress, BedrockPreConnection>()
    data class BedrockPreConnection(val address: SocketAddress, val state: BedrockState)
    enum class BedrockState { OPEN, CONNECT }
}