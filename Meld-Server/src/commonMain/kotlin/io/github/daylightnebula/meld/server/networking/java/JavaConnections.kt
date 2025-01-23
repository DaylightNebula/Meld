package io.github.daylightnebula.meld.server.networking.java

import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.server.ConnectionAbortedEvent
import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.common.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import kotlinx.io.IOException

class JavaConnection(
    val socket: Socket,
    val read: ChannelReader,
    val write: ByteWriteChannel,
): IConnection<JavaPacket> {
    var state: JavaConnectionState = JavaConnectionState.HANDSHAKE

    override fun sendPacket(packet: JavaPacket) {
        // create writer
        val writer = ByteWriter(packet.ID, DataPacketMode.JAVA)

        // encode packet
        packet.encode(writer)

        // send byte array to client
        val bytes = writer.getData()
        val me = this
        runBlocking {
            // if write fails, the connection is aborted
            try {
                write.writeFully(bytes, 0, bytes.size)
            } catch (ex: IOException) {
//                if (state == JavaConnectionState.IN_GAME) println("Connection $me aborted")
                EventBus.callEvent(ConnectionAbortedEvent(me))
                Meld.connections.remove(me as IConnection<*>)
            } catch (ex: IllegalStateException) { println("Fix parallel write issues!") } // FIXME
        }
    }

    override fun toString(): String {
        return "JavaConnection(address=${socket.remoteAddress},state=$state)"
    }
}

// the connection state of a java connection
enum class JavaConnectionState { HANDSHAKE, STATUS, LOGIN, CONFIG, IN_GAME }

// representation of a java packet that can be sent too and from users
interface JavaPacket {
    interface Creator<T: JavaPacket> {
        val ID: Int
        val STATE: JavaConnectionState
        fun decode(reader: IReader): T
    }

    val ID: Int
    val STATE: JavaConnectionState
    fun encode(writer: ByteWriter)
}