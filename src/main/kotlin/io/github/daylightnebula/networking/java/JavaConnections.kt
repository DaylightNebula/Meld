package io.github.daylightnebula.networking.java

import io.github.daylightnebula.networking.common.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking

class JavaConnection(
    val socket: ASocket,
    val read: ChannelReader,
    val write: ByteWriteChannel,
    var state: JavaConnectionState =
        JavaConnectionState.HANDSHAKE
): IConnection<JavaPacket> {
    override fun sendPacket(packet: JavaPacket) {
        // create writer
        val writer = ByteWriter(packet.id, DataPacketMode.BEDROCK)

        // encode packet
        packet.encode(writer)

        // send byte array to client
        val bytes = writer.getData()
        runBlocking {
            write.writeFully(bytes, 0, bytes.size)
        }
    }
}

enum class JavaConnectionState { HANDSHAKE, STATUS, LOGIN, IN_GAME }

interface JavaPacket {
    val id: Int
    val type: JavaPacket
    fun decode(reader: AbstractReader)
    fun encode(writer: ByteWriter)
}