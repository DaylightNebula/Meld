package io.github.daylightnebula.meld.server

import io.github.daylightnebula.meld.server.events.EventHandler
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.IConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.utils.NotImplementedException
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.*

annotation class PacketHandler

val JavaPacketRegistry = hashMapOf<Pair<Int, JavaConnectionState>, () -> JavaPacket>()

// singleton to handle incoming packets
object PacketManager {
    // global list of all registered packet handlers
    private val packetListeners = hashMapOf<KType, MutableList<Pair<PacketBundle, KFunction<*>>>>()

    // handle incoming java packets
    fun handleJavaPacket(connection: JavaConnection, packetID: Int, reader: AbstractReader) {
        // attempt to find an initializer for the given packet id and the connections state
        val packet = JavaPacketRegistry[packetID to connection.state]?.let { it() }
        if (packet == null) {
            println("WARN no java packet registered for id $packetID and state ${connection.state}")
            return
        }

        // decode the packet
        packet.decode(reader)

        // handle the packet
        handlePacket(connection, packet)
    }

    // handle an incoming packet
    fun <T: Any> handlePacket(connection: IConnection<T>, packet: T) =
        packetListeners[packet::class.starProjectedType]?.forEach { it.second.call(it.first, connection, packet) }
            ?: println("WARN not handling packet $packet")

    // add the given bundle to the list of handlers
    fun register(bundle: PacketBundle) {
        // register java packets
        JavaPacketRegistry.putAll(bundle.registerJavaPackets())

        // load all packet handler functions from the given listener
        bundle::class.declaredMemberFunctions
            .filter { it.findAnnotation<PacketHandler>() != null }
            .forEach { func ->
                // only 1 parameter
                if (func.valueParameters.size != 2) return@forEach

                // make sure that parameter is an event
                if (!checkParameterInheritance(func.valueParameters[0], JavaConnection::class)) return@forEach
                if (!checkParameterInheritance(func.valueParameters[1], JavaPacket::class) && !checkParameterInheritance(func.valueParameters[1], BedrockPacket::class)) return@forEach

                // get list of functions for the given param type
                val param = func.valueParameters[1]
                var list = packetListeners[param.type]
                if (list == null) {
                    list = mutableListOf()
                    packetListeners[param.type] = list
                }

                // save event
                list.add(bundle to func)
            }

        println("Registered packet bundle: $bundle")
    }

    // function to check if a parameter inherits from the given class
    private fun checkParameterInheritance(parameter: KParameter, className: KClass<*>) =
        parameter.type.classifier?.let { it as? KClass<*> }?.isSubclassOf(className) ?: false
}

// class to represent packet bundles
interface PacketBundle {
    fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, ()  -> JavaPacket>
}

// helper functions to make some packets as no encode or decode
fun noEncode(): Unit = throw NotImplementedException("Function marked no encode!")
fun noDecode(): Unit = throw NotImplementedException("Function marked no decode!")

// functions to make making bundles easier
fun bedrock(
    vararg handlers: Pair<String, (connection: BedrockConnection, packet: BedrockPacket) -> Unit>
) = hashMapOf(*handlers)

fun java(
    vararg handlers: Pair<String, (connection: JavaConnection, packet: JavaPacket) -> Unit>
) = hashMapOf(*handlers)

fun javaPackets(
    vararg map: Pair<Pair<Int, JavaConnectionState>, () -> JavaPacket>
) = hashMapOf(*map)

fun javaPacketID(
    id: Int,
    state: JavaConnectionState
) = id to state

fun javaGamePacket(
    id: Int
) = id to JavaConnectionState.IN_GAME