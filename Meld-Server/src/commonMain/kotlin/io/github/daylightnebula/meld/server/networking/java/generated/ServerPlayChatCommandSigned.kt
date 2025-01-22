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
class ServerPlayChatCommandSigned(
	val command: String,
	val timestamp: Long,
	val salt: Long,
	val prefixedArray}}(8): Array<String>,
	val signature: ByteArray,
	val messageCount: Int,
	val acknowledged: Fixed BitSet
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayChatCommandSigned> {
        override val ID: Int = 0x06
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayChatCommandSigned = ServerPlayChatCommandSigned(
			command = reader.readString(),
			timestamp = reader.readLong(),
			salt = reader.readLong(),
			prefixedArray}}(8) = reader.readArray { reader.readString() },
			signature = reader.readByteArray(),
			messageCount = reader.readVarInt(),
			acknowledged = reader.readFixed BitSet()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeString(command)
		writer.writeLong(timestamp)
		writer.writeLong(salt)
		writer.writeArray(prefixedArray}}(8)) { prefixedArray}}(8) -> writer.writeString(prefixedArray}}(8)) }
		writer.writeByteArray(signature)
		writer.writeVarInt(messageCount)
		writer.writeFixed BitSet(acknowledged)
	}
}
