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
class ServerPlayChat(
	val message: String,
	val timestamp: Long,
	val salt: Long,
	val signature: ByteArray?,
	val messageCount: Int,
	val acknowledged: Fixed BitSet
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayChat> {
        override val ID: Int = 0x07
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayChat = ServerPlayChat(
			message = reader.readString(),
			timestamp = reader.readLong(),
			salt = reader.readLong(),
			signature = reader.readOptional { reader.readByteArray() },
			messageCount = reader.readVarInt(),
			acknowledged = reader.readFixed BitSet()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeString(message)
		writer.writeLong(timestamp)
		writer.writeLong(salt)
		writer.writeOptional(signature) { signature -> writer.writeByteArray(signature) }
		writer.writeVarInt(messageCount)
		writer.writeFixed BitSet(acknowledged)
	}
}
