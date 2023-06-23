package io.github.daylightnebula.networking.bedrock

import io.github.daylightnebula.networking.common.DataPacketMode
import io.github.daylightnebula.networking.common.RawPacket

class FrameSetPacket(
   val connection: BedrockNetworkController.BedrockPreConnection,
   val reliability: Reliability,
   id: Int
): RawPacket(id, DataPacketMode.BEDROCK) {

   fun encode(): ByteArray {
      // get output data
      val data = getRawData()

      // create new packet for output
      val packet = RawPacket(132, DataPacketMode.BEDROCK)
      packet.writeByte(132.toByte())
      packet.write3Int(connection.packetNumber++)           // SEQUENCE ID
      packet.writeByte(reliability.toRaw().toByte())                // FLAGS
      packet.writeUShort((data.size * 8).toUShort())        // LENGTH
      packet.write3Int(0)                              // FRAME INDEX
      packet.writeByte(id.toUByte().toByte())               // packet ID
      packet.writeByteArray(data)                          // DATA
      return packet.getRawData()
   }
}