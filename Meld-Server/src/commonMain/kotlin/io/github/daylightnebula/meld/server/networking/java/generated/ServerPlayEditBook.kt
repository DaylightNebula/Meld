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
class ServerPlayEditBook(
	val slot: Int,
	val entries: Array<String>,
	val title: String?
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayEditBook> {
        override val ID: Int = 0x16
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayEditBook = ServerPlayEditBook(
			slot = reader.readVarInt(),
			entries = reader.readArray { reader.readString() },
			title = reader.readOptional { reader.readString() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(slot)
		writer.writeArray(entries) { entries -> writer.writeString(entries) }
		writer.writeOptional(title) { title -> writer.writeString(title) }
	}
}
