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
class ClientPlayUpdateRecipes(
	val prefixedArray}}: Array<String>,
	val items: Array<Int>,
	val prefixedArray}}: Array<ID Set>,
	val slotDisplay: Slot Display
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayUpdateRecipes> {
        override val ID: Int = 0x7E
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayUpdateRecipes = ClientPlayUpdateRecipes(
			prefixedArray}} = reader.readArray { reader.readString() },
			items = reader.readArray { reader.readVarInt() },
			prefixedArray}} = reader.readArray { reader.readID Set() },
			slotDisplay = reader.readSlot Display()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeString(prefixedArray}}) }
		writer.writeArray(items) { items -> writer.writeVarInt(items) }
		writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeID Set(prefixedArray}}) }
		writer.writeSlot Display(slotDisplay)
	}
}
