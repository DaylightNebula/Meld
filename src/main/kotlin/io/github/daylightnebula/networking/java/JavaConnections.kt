package io.github.daylightnebula.networking.java

import io.github.daylightnebula.networking.common.*
import io.github.daylightnebula.player.Player
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking

class JavaConnection(
    val socket: Socket,
    val read: ChannelReader,
    val write: ByteWriteChannel,
    var state: JavaConnectionState =
        JavaConnectionState.HANDSHAKE
): IConnection<JavaPacket> {
    override var player: Player? = null

    override fun sendPacket(packet: JavaPacket) {
        // create writer
        val writer = ByteWriter(packet.id, DataPacketMode.JAVA)

        // encode packet
        packet.encode(writer)

        // send byte array to client
        val bytes = writer.getData()
        runBlocking {
            write.writeFully(bytes, 0, bytes.size)
        }
    }

    override fun toString(): String {
        return "JavaConnection(address=${socket.remoteAddress},state=$state)"
    }
}

enum class JavaConnectionState { HANDSHAKE, STATUS, LOGIN, IN_GAME }

interface JavaPacket {
    val id: Int
    fun decode(reader: AbstractReader)
    fun encode(writer: ByteWriter)
}