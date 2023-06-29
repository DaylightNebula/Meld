package io.github.daylightnebula

import io.github.daylightnebula.networking.bedrock.BedrockConnection
import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.IConnection
import io.github.daylightnebula.networking.java.JavaConnection
import io.github.daylightnebula.networking.java.JavaConnectionState
import jdk.jshell.spi.ExecutionControl.NotImplementedException
import net.minestom.server.network.packet.client.JavaClientPacket
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket

val JavaPacketRegistry = hashMapOf<Pair<Int, JavaConnectionState>, () -> JavaClientPacket>()

// singleton to handle incoming packets
object PacketHandler {
    // global list of all registered packet handlers
    private val bedrockHandlers = hashMapOf<String, (connection: BedrockConnection, packet: BedrockPacket) -> Unit>()
    private val javaHandlers = hashMapOf<String, (connection: JavaConnection, packet: JavaClientPacket) -> Unit>()

    // handle incoming java packets
    fun handleJavaPacket(connection: JavaConnection, packetID: Int, reader: AbstractReader) {
        // attempt to find an initializer for the given packet id and the connections state
        val packet = JavaPacketRegistry[packetID to connection.state]?.let { it() }
        if (packet == null) {
            println("WARN no java packet registered for id $packetID and state ${connection.state}")
            return
        }

        // decode the packet
//        packet.decode(reader)

        // handle the packet
        handlePacket(connection, packet)
    }

    // handle an incoming packet
    fun <T: Any> handlePacket(connection: IConnection<T>, packet: T) {
        when(connection) {
            is BedrockConnection -> bedrockHandlers[packet.javaClass.name]?.let { it(connection, packet as BedrockPacket) }
                ?: println("WARN no bedrock packet registered for name ${packet.javaClass.name}")

            is JavaConnection -> javaHandlers[packet.javaClass.name]?.let { it(connection, packet as JavaClientPacket) }
                ?: println("WARN no java packet registered for name ${packet.javaClass.name}")
        }
    }

    // add the given bundle to the list of handlers
    fun register(bundle: PacketBundle) {
        // save packet handlers
        bedrockHandlers.putAll(bundle.bedrock)
        javaHandlers.putAll(bundle.java)

        // register java packets
        JavaPacketRegistry.putAll(bundle.registerJavaPackets())

        println("Registered packet bundle: $bundle")
    }
}

// class to represent packet bundles
abstract class PacketBundle(
    val bedrock: HashMap<String, (connection: BedrockConnection, packet: BedrockPacket) -> Unit> = hashMapOf(),
    val java: HashMap<String, (connection: JavaConnection, packet: JavaClientPacket) -> Unit> = hashMapOf()
) {
    abstract fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, ()  -> JavaClientPacket>
}

// helper functions to make some packets as no encode or decode
fun noEncode() {
    throw NotImplementedException("Function marked no encode!")
}

fun noDecode() {
    throw NotImplementedException("Function marked no decode!")
}

// functions to make making bundles easier
fun bedrock(
    vararg handlers: Pair<String, (connection: BedrockConnection, packet: BedrockPacket) -> Unit>
) = hashMapOf(*handlers)

fun java(
    vararg handlers: Pair<String, (connection: JavaConnection, packet: JavaClientPacket) -> Unit>
) = hashMapOf(*handlers)

fun javaPackets(
    vararg map: Pair<Pair<Int, JavaConnectionState>, () -> JavaClientPacket>
) = hashMapOf(*map)

fun javaPacketID(
    id: Int,
    state: JavaConnectionState
) = id to state