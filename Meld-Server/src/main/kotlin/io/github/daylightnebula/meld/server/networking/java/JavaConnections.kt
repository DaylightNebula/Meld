package io.github.daylightnebula.meld.server.networking.java

import io.github.daylightnebula.meld.server.ConnectionAbortedEvent
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.common.*
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

    override fun sendPacket(packet: JavaPacket) {
        // create writer
        val writer = ByteWriter(packet.id, DataPacketMode.JAVA)

        // encode packet
        packet.encode(writer)

        // send byte array to client
        val bytes = writer.getData()
        val me = this
        runBlocking {
            // if write fails, the connection is aborted
            try {
                write.writeFully(bytes, 0, bytes.size)
            } catch (ex: Exception) {
                if (state == JavaConnectionState.IN_GAME) println("Connection $me aborted")
                EventBus.callEvent(ConnectionAbortedEvent(me))
                Meld.connections.remove(me as IConnection<*>)
            }
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