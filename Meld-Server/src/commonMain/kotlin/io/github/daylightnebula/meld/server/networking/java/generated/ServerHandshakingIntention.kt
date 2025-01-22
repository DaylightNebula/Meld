package io.github.daylightnebula.meld.server.networking.java.generated

import dev.romainguy.kotlin.math.*
import io.github.daylightnebula.meld.server.networking.*
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import kotlinx.serialization.json.JsonObject
import net.benwoodworth.knbt.NbtCompound
import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class ServerHandshakingIntention(
	val protocolVersion: Int,
	val serverAddress: String,
	val serverPort: UShort,
	val nextState: Int
): JavaPacket {
    companion object: JavaPacket.Creator<ServerHandshakingIntention> {
        override val ID: Int = 0x00
        override val STATE: JavaConnectionState = JavaConnectionState.HANDSHAKE
        override fun decode(reader: AbstractReader): ServerHandshakingIntention = ServerHandshakingIntention(
			protocolVersion = reader.readVarInt(),
			serverAddress = reader.readString(),
			serverPort = reader.readUShort(),
			nextState = reader.readVarInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(protocolVersion)
		writer.writeString(serverAddress)
		writer.writeUShort(serverPort)
		writer.writeVarInt(nextState)
	}
}
