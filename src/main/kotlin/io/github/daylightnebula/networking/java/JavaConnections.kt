package io.github.daylightnebula.networking.java

import io.github.daylightnebula.networking.common.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.runBlocking
import net.minestom.server.network.NetworkBuffer
import net.minestom.server.network.PacketUtils
import net.minestom.server.network.packet.client.JavaClientPacket
import net.minestom.server.network.packet.server.JavaServerPacket
import java.nio.ByteBuffer

class JavaConnection(
    val socket: Socket,
    val read: ChannelReader,
    val write: ByteWriteChannel,
    var state: JavaConnectionState =
        JavaConnectionState.HANDSHAKE
): IConnection<JavaServerPacket> {
    override fun sendPacket(packet: JavaServerPacket) {
        // create writer
        val writer = ByteWriter((0), DataPacketMode.JAVA)

        // encode packet
//        packet.encode(writer)

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