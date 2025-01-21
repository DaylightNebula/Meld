package io.github.daylightnebula.meld.server

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.common.Packet
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.utils.NotImplementedException
import kotlin.reflect.KClass

val JavaPacketRegistry = hashMapOf<JavaPacketKey, JavaPacketEntry<*>>()

data class JavaPacketKey(
    val id: Int,
    val state: JavaConnectionState
)

data class JavaPacketEntry<T: JavaPacket>(
    val key: JavaPacketKey,
    val creator: JavaPacket.Creator<T>,
    val execute: (JavaConnection, T) -> Unit
) {
    fun buildAndExecute(connection: JavaConnection, reader: AbstractReader) {
        val packet = creator.decode(reader)
        execute.invoke(connection, packet)
    }
}

// singleton to handle incoming packets
object PacketManager {
    // handle incoming java packets
    fun handleJavaPacket(connection: JavaConnection, packetID: Int, reader: AbstractReader) {
        // attempt to find an initializer for the given packet id and the connections state
        val key = JavaPacketKey(packetID, connection.state)
        if (!JavaPacketRegistry.containsKey(key)) {
            println("WARN no java packet registered for id $packetID and state ${connection.state}")
            return
        }

        // build and execute
        JavaPacketRegistry[key]!!.buildAndExecute(connection, reader)
    }

    // add the given bundle to the list of handlers
    fun register(bundle: PacketBundle) {
        // register java packets
        val packets = bundle.registerJavaPackets()
        JavaPacketRegistry.putAll(packets)
    }
}

// class to represent packet bundles
interface PacketBundle {
    fun registerJavaPackets(): Map<JavaPacketKey, JavaPacketEntry<*>>
}

// helper functions to make some packets as no encode or decode
fun noEncode(): Unit = throw NotImplementedException("Function marked no encode!")
fun noDecode(): Unit = throw NotImplementedException("Function marked no decode!")

fun java(
    vararg handlers: Pair<JavaPacketKey, JavaPacketEntry<*>>
) = mapOf(*handlers)

fun javaPackets(
    vararg map: Pair<JavaPacketKey, JavaPacketEntry<*>>
) = hashMapOf(*map)

fun <T: JavaPacket> javaPacket(
    creator: JavaPacket.Creator<T>,
    execute: (JavaConnection, T) -> Unit
) = javaPacket(creator.ID, creator.STATE, creator, execute)

fun <T: JavaPacket> javaPacket(
    id: Int,
    state: JavaConnectionState,
    creator: JavaPacket.Creator<T>,
    execute: (JavaConnection, T) -> Unit
): Pair<JavaPacketKey, JavaPacketEntry<T>> {
    val key = JavaPacketKey(id, state)
    val entry = JavaPacketEntry(key, creator, execute)
    return key to entry
}

fun javaGamePacket(
    id: Int
) = id to JavaConnectionState.IN_GAME