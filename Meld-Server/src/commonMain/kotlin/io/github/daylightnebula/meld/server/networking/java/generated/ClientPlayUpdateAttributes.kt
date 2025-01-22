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
class ClientPlayUpdateAttributes(
	val entityId: Int,
	val prefixedArray}}: Array<Int>,
	val value: Double,
	val modifiers: Array<ModifierData>
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayUpdateAttributes> {
        override val ID: Int = 0x7C
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayUpdateAttributes = ClientPlayUpdateAttributes(
			entityId = reader.readVarInt(),
			prefixedArray}} = reader.readArray { reader.readVarInt() },
			value = reader.readDouble(),
			modifiers = reader.readArray { reader.readModifierData() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeVarInt(prefixedArray}}) }
		writer.writeDouble(value)
		writer.writeArray(modifiers) { modifiers -> writer.writeModifierData(modifiers) }
	}
}
