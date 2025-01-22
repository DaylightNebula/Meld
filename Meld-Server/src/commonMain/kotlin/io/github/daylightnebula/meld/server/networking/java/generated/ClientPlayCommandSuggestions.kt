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
class ClientPlayCommandSuggestions(
	val id: Int,
	val start: Int,
	val length: Int,
	val match: Array<CommandSuggestion>
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayCommandSuggestions> {
        override val ID: Int = 0x10
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayCommandSuggestions = ClientPlayCommandSuggestions(
			id = reader.readVarInt(),
			start = reader.readVarInt(),
			length = reader.readVarInt(),
			match = reader.readArray { reader.readCommandSuggestion() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(id)
		writer.writeVarInt(start)
		writer.writeVarInt(length)
		writer.writeArray(match) { match -> writer.writeCommandSuggestion(match) }
	}
}
