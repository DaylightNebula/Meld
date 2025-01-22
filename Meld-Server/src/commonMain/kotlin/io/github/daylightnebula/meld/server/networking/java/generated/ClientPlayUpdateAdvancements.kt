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
class ClientPlayUpdateAdvancements(
	val reset/clear: Boolean,
	val prefixedArray}}: Array<String>,
	val value: Advancement,
	val identifiers: Array<String>,
	val prefixedArray}}: Array<String>,
	val value: AdvancementProgress
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayUpdateAdvancements> {
        override val ID: Int = 0x7B
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayUpdateAdvancements = ClientPlayUpdateAdvancements(
			reset/clear = reader.readBoolean(),
			prefixedArray}} = reader.readArray { reader.readString() },
			value = reader.readAdvancement(),
			identifiers = reader.readArray { reader.readString() },
			prefixedArray}} = reader.readArray { reader.readString() },
			value = reader.readAdvancementProgress()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeBoolean(reset/clear)
		writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeString(prefixedArray}}) }
		writer.writeAdvancement(value)
		writer.writeArray(identifiers) { identifiers -> writer.writeString(identifiers) }
		writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeString(prefixedArray}}) }
		writer.writeAdvancementProgress()
	}
}
