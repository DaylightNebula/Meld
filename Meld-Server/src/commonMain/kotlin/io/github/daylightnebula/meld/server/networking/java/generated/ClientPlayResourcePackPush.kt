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
class ClientPlayResourcePackPush(
	val uuid: Uuid,
	val url: String,
	val hash: String,
	val forced: Boolean,
	val promptMessage: JsonObject?
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayResourcePackPush> {
        override val ID: Int = 0x4B
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayResourcePackPush = ClientPlayResourcePackPush(
			uuid = reader.readUuid(),
			url = reader.readString(),
			hash = reader.readString(),
			forced = reader.readBoolean(),
			promptMessage = reader.readOptional { reader.readJsonObject() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeUuid(uuid)
		writer.writeString(url)
		writer.writeString(hash)
		writer.writeBoolean(forced)
		writer.writeOptional(promptMessage) { promptMessage -> writer.writeJsonObject(promptMessage) }
	}
}
