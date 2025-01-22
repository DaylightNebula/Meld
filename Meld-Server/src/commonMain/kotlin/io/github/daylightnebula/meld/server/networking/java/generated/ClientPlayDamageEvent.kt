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
class ClientPlayDamageEvent(
	val entityId: Int,
	val sourceTypeId: Int,
	val sourceCauseId: Int,
	val sourceDirectId: Int,
	val hasSourcePosition: Boolean,
	val sourcePositionX: Double?,
	val sourcePositionY: Double?,
	val sourcePositionZ: Double?
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayDamageEvent> {
        override val ID: Int = 0x1A
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayDamageEvent = ClientPlayDamageEvent(
			entityId = reader.readVarInt(),
			sourceTypeId = reader.readVarInt(),
			sourceCauseId = reader.readVarInt(),
			sourceDirectId = reader.readVarInt(),
			hasSourcePosition = reader.readBoolean(),
			sourcePositionX = reader.readOptional { reader.readDouble() },
			sourcePositionY = reader.readOptional { reader.readDouble() },
			sourcePositionZ = reader.readOptional { reader.readDouble() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeVarInt(sourceTypeId)
		writer.writeVarInt(sourceCauseId)
		writer.writeVarInt(sourceDirectId)
		writer.writeBoolean(hasSourcePosition)
		writer.writeOptional(sourcePositionX) { sourcePositionX -> writer.writeDouble(sourcePositionX) }
		writer.writeOptional(sourcePositionY) { sourcePositionY -> writer.writeDouble(sourcePositionY) }
		writer.writeOptional(sourcePositionZ) { sourcePositionZ -> writer.writeDouble(sourcePositionZ) }
	}
}
