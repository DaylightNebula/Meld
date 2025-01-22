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
class ClientLoginCustomQuery(
	val messageId: Int,
	val channel: String,
	val data: ByteArray
): JavaPacket {
    companion object: JavaPacket.Creator<ClientLoginCustomQuery> {
        override val ID: Int = 0x04
        override val STATE: JavaConnectionState = JavaConnectionState.LOGIN
        override fun decode(reader: AbstractReader): ClientLoginCustomQuery = ClientLoginCustomQuery(
			messageId = reader.readVarInt(),
			channel = reader.readString(),
			data = reader.readByteArray()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(messageId)
		writer.writeString(channel)
		writer.writeByteArray(data)
	}
}
