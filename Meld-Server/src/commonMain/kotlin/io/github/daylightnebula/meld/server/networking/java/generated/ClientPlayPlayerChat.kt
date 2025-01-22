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
class ClientPlayPlayerChat(
	val sender: Uuid,
	val index: Int,
	val messageSignatureBytes: ByteArray?,
	val string}}(256): String,
	val timestamp: Long,
	val salt: Long,
	val messageId: Array<Int>,
	val signature: ByteArray?,
	val unsignedContent: JsonObject?,
	val filterType: Int,
	val filterTypeBits: BitSet?,
	val chatType: Int,
	val textComponent}}: JsonObject,
	val textComponent}}: JsonObject?
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayPlayerChat> {
        override val ID: Int = 0x3B
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayPlayerChat = ClientPlayPlayerChat(
			sender = reader.readUuid(),
			index = reader.readVarInt(),
			messageSignatureBytes = reader.readOptional { reader.readByteArray() },
			string}}(256) = reader.readString(),
			timestamp = reader.readLong(),
			salt = reader.readLong(),
			messageId = reader.readArray { reader.readVarInt() },
			signature = reader.readOptional { reader.readByteArray() },
			unsignedContent = reader.readOptional { reader.readJsonObject() },
			filterType = reader.readVarInt(),
			filterTypeBits = reader.readOptional { reader.readBitSet() },
			chatType = reader.readVarInt(),
			textComponent}} = reader.readJsonObject(),
			textComponent}} = reader.readOptional { reader.readJsonObject() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeUuid(sender)
		writer.writeVarInt(index)
		writer.writeOptional(messageSignatureBytes) { messageSignatureBytes -> writer.writeByteArray(messageSignatureBytes) }
		writer.writeString(string}}(256))
		writer.writeLong(timestamp)
		writer.writeLong(salt)
		writer.writeArray(messageId) { messageId -> writer.writeVarInt(messageId) }
		writer.writeOptional(signature) { signature -> writer.writeByteArray(signature) }
		writer.writeOptional(unsignedContent) { unsignedContent -> writer.writeJsonObject(unsignedContent) }
		writer.writeVarInt(filterType)
		writer.writeOptional(filterTypeBits) { filterTypeBits -> writer.writeBitSet(filterTypeBits) }
		writer.writeVarInt(chatType)
		writer.writeJsonObject(textComponent}})
		writer.writeOptional(textComponent}}) { textComponent}} -> writer.writeJsonObject(textComponent}}) }
	}
}
