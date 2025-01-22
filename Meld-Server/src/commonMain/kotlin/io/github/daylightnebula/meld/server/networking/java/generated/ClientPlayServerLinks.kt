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
class ClientPlayServerLinks(
	val prefixedArray}}: Array<Boolean>,
	val label: Int,
	val url: String
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayServerLinks> {
        override val ID: Int = 0x82
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayServerLinks = ClientPlayServerLinks(
			prefixedArray}} = reader.readArray { reader.readBoolean() },
			label = reader.readVarInt(),
			url = reader.readString()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeBoolean(prefixedArray}}) }
		writer.writeVarInt(label)
		writer.writeString(url)
	}
}
