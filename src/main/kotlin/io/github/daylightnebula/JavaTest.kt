package io.github.daylightnebula

import com.github.steveice10.packetlib.Server
import com.github.steveice10.packetlib.Session
import com.github.steveice10.packetlib.codec.BasePacketCodecHelper
import com.github.steveice10.packetlib.codec.PacketCodecHelper
import com.github.steveice10.packetlib.event.server.ServerAdapter
import com.github.steveice10.packetlib.event.session.ConnectedEvent
import com.github.steveice10.packetlib.event.session.DisconnectedEvent
import com.github.steveice10.packetlib.event.session.DisconnectingEvent
import com.github.steveice10.packetlib.event.session.SessionAdapter
import com.github.steveice10.packetlib.packet.DefaultPacketHeader
import com.github.steveice10.packetlib.packet.Packet
import com.github.steveice10.packetlib.packet.PacketHeader
import com.github.steveice10.packetlib.packet.PacketProtocol
import com.github.steveice10.packetlib.tcp.TcpServer
import java.util.function.Supplier


fun main() {
    val server = TcpServer("127.0.0.1", 25565, Supplier<PacketProtocol> { TestProtocol() })
    server.addListener(object : ServerAdapter() {

    })
    server.bind()
}

class TestProtocol(): PacketProtocol() {
    private val header = DefaultPacketHeader()

    // simples
    override fun getSRVRecordPrefix(): String = "_meld_java"
    override fun getPacketHeader(): PacketHeader = header
    override fun createHelper(): PacketCodecHelper = BasePacketCodecHelper()
    override fun newClientSession(session: Session?) = TODO("Not yet implemented")

    // on new session, add new adapter instance
    override fun newServerSession(server: Server, session: Session) {
        session.addListener(object : SessionAdapter() {
            override fun packetReceived(session: Session, packet: Packet) {
                println("Received packet $packet")
//                if (packet is PingPacket) {
//                    System.out.println("SERVER Received: " + (packet as PingPacket).getPingId())
//                    session.send(packet)
//                }
            }

            override fun packetSent(session: Session?, packet: Packet) {
//                if (packet is PingPacket) {
//                    System.out.println("SERVER Sent: " + (packet as PingPacket).getPingId())
//                }
            }

            override fun connected(event: ConnectedEvent?) {
                println("SERVER Connected")
            }

            override fun disconnecting(event: DisconnectingEvent) {
                println("SERVER Disconnecting: " + event.reason)
            }

            override fun disconnected(event: DisconnectedEvent) {
                println("SERVER Disconnected: " + event.reason)
            }
        })
    }
}