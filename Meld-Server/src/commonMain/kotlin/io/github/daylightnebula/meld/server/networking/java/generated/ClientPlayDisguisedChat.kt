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
class ClientPlayDisguisedChat(
	val message: JsonObject,
	val chatType: Int,
	val textComponent}}: JsonObject,
	val textComponent}}: JsonObject?
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayDisguisedChat> {
        override val ID: Int = 0x1E
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayDisguisedChat = ClientPlayDisguisedChat(
			message = reader.readJsonObject(),
			chatType = reader.readVarInt(),
			textComponent}} = reader.readJsonObject(),
			textComponent}} = reader.readOptional { reader.readJsonObject() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeJsonObject(message)
		writer.writeVarInt(chatType)
		writer.writeJsonObject(textComponent}})
		writer.writeOptional(textComponent}}) { textComponent}} -> writer.writeJsonObject(textComponent}}) }
	}
}
