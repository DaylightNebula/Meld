package io.github.daylightnebula.networking.bedrock

import io.github.daylightnebula.Meld
import io.github.daylightnebula.networking.common.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
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
   private val listener = thread(start = false) {
      // start infinite read loop
      while(true) {
         runBlocking {
            // receive packet and check if there is a connection for that address
            val received = serverSocket.receive()
            val connection = preConnections[received.address]

            // read packet and its header
            val reader = ByteReadPacketReader(received.packet)
            var packetID = reader.nextByte().toInt()

            // make sure packet id is positive
            if (packetID < 0)
               packetID += 256

            when(packetID) {
               // ping packet
               0x00 -> {
                  val time = reader.readLong()
                  val packet = RawPacket(0x03, DataPacketMode.BEDROCK)
                  packet.writeLong(time)
                  packet.writeLong(System.currentTimeMillis())
                  serverSocket.send(Datagram(ByteReadPacket(packet.getData()), received.address))
               }

               // unconnected ping
               0x01, 0x02 -> {
                  // create packet response
                  val dataPacket = RawPacket(0x1c, DataPacketMode.BEDROCK)
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
                     val packet = RawPacket(0x06, DataPacketMode.BEDROCK)
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
                     val packet = RawPacket(0x08, DataPacketMode.BEDROCK)
                     packet.writeByteArray(BedrockMagic.bytes)
                     packet.writeLong(serverUUID.mostSignificantBits)
                     packet.writeByteArray(address)
                     packet.writeUShort(mtu)
                     packet.writeBoolean(false)
                     serverSocket.send(Datagram(ByteReadPacket(packet.getData()), received.address))

                     // save connection
                     preConnections[received.address] = BedrockPreConnection(received.address, address, clientGUID, BedrockState.OPEN)
                  } else
                     println("Open connection request 2 with invalid magic")
               }

               // frame set packet
               in 0x80 .. 0x8d -> {
                  // make sure a connection is found
                  if (connection == null) {
                     println("Frame set packet received from connection without registered connection!")
                     return@runBlocking
                  }

                  // unpack
                  val sequenceID = reader.read3Int()

                  // read frames
                  var sendAck = false
                  while(reader.remaining() > 0) {
                     sendAck = handleFrame(connection, received, reader) || sendAck
                  }

                  // acknowledge the packet
                  if (sendAck) {
                     val packet = RawPacket(0xC0, DataPacketMode.BEDROCK)
                     packet.writeShort(1)
                     packet.writeBoolean(true)
                     packet.write3Int(sequenceID)
                     serverSocket.send(Datagram(ByteReadPacket(packet.getData()), received.address))
                  }
               }

               // otherwise, unknown packet
               else -> println("WARN Unknown Reknet packet of ID ${packetID.toInt().toString(16).padStart(2, '0')} with length ${received.packet.remaining}")
            }
         }
      }
   }

   private suspend fun handleFrame(connection: BedrockPreConnection, datagram: Datagram, reader: AbstractReader): Boolean {
      // read header
      val flags = reader.nextByte().toInt()
      val reliability = Reliability.fromRaw(flags)
      val hasFragment = flags and Flags.PACKET_PAIR.id() != 0
      val length = reader.readUShort() / 8u // in bits for some ready

      // handle each type of packet
      when(reliability) {
         // process reliable packets
         Reliability.RELIABLE -> {
            val index = reader.read3Int()
            val body = reader.readArray(length.toInt())
            handleProcessedPacket(connection, datagram, ByteArrayReader(body))
         }

         Reliability.RELIABLE_ORDERED -> {
            val index = reader.read3Int()
            val orderIndex = reader.read3Int()
            val channel = reader.nextByte()
            val body = reader.readArray(length.toInt())
            handleProcessedPacket(connection, datagram, ByteArrayReader(body))
         }

         Reliability.UNRELIABLE -> {
            val body  = reader.readArray(length.toInt())
            handleProcessedPacket(connection, datagram, ByteArrayReader(body))
         }

         else -> println("WARN $reliability handler not implemented")
      }

      // return sequence id for acknowledgement
      return reliability.sendAck
   }

   private suspend fun handleProcessedPacket(connection: BedrockPreConnection, datagram: Datagram, reader: AbstractReader) {
      var packetID = reader.nextByte().toInt()

      // make sure packet id is positive
      if (packetID < 0)
         packetID += 256

      when (packetID) {
         // connection request
         0x09 -> {
            // handle packet
            val guid = reader.readLong()
            val time = reader.readLong()

            println("Connection request from $guid at time $time")

            // build response packet
            val packet = FrameSetPacket(connection, Reliability.RELIABLE, 0x10)

            // address and index
            packet.writeByteArray(connection.byteAddress)
            packet.writeShort(0)

            // add internal IDs (just 10x 255.255.255.255:19132)
            (0 until 10).forEach { _ ->
               val array = byteArrayOf(4, 0, 0, 0, 0)
               packet.writeByteArray(array)
               packet.writeUShort((0).toUShort())
            }

            // write timestamps
            packet.writeLong(time)
            packet.writeLong(System.currentTimeMillis())

            // send response
            serverSocket.send(Datagram(ByteReadPacket(packet.encode()), datagram.address))
         }

         // new connection packet, proceeds to playing state
         0x13 -> {
            // unpack
            val address = reader.readArray(7)
            val internalAddress = reader.readArray(7)

            // move connection to playing
            connection.state = BedrockState.PLAYING
         }

         // disconnect packet
         0x15 -> {
            preConnections.remove(connection.address)
         }

         // ping packet
         0x00 -> {
            // unpack
            val time = reader.readLong()

            // send pack pong packet
            val packet = FrameSetPacket(connection, Reliability.UNRELIABLE, 0x03)
            packet.writeLong(time)
            packet.writeLong(System.currentTimeMillis())
            serverSocket.send(Datagram(ByteReadPacket(packet.encode()), datagram.address))
         }

         // game packet handler
         0xFE -> {
            handleGamePacket(connection, reader)
//            // unpack
//            val extra = reader.readShort()
//            val packetID = reader.nextByte()
//            val protocol = reader.readInt()
//
//            // read extra bytes
//            print("Game Packet $extra $packetID $protocol: ")
//            while(reader.remaining() > 0) {
//               var str = reader.nextByte().toUInt().toString(16).padStart(2, '0').uppercase()
//               if (str.length > 2) str = str.substring(str.length - 2, str.length)
//               print("$str ")
//            }
//            println()


         }

         else -> println("WARN Processed handler for packet with ID ${packetID.toString(16).padStart(2, '0')} is not implemented")
      }
   }

   suspend fun handleGamePacket(connection: BedrockPreConnection, reader: AbstractReader) {
      val extra = reader.readShort()
      val packetID = reader.nextByte().toInt()
      println("PacketID: $packetID")

      when(packetID) {
         0x01 -> {
            val packet = FrameSetPacket(connection, Reliability.UNRELIABLE, 0x03)
            serverSocket.send(Datagram(ByteReadPacket(packet.encode()), connection.address))
         }

          else -> println("Unknown game packet $packetID")
      }
   }

   fun getBit(value: Int, position: Int): Int {
      return (value shr position) and 1;
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

   private val preConnections = hashMapOf<SocketAddress, BedrockPreConnection>()
   data class BedrockPreConnection(val address: SocketAddress, val byteAddress: ByteArray, val clientGUID: Long, var state: BedrockState, var packetNumber: Int = 0)
   enum class BedrockState { OPEN, PLAYING }
}