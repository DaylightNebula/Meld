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
class ClientPlayPlayerLookAt(
	val feet/eyes: Int,
	val targetX: Double,
	val targetY: Double,
	val targetZ: Double,
	val isEntity: Boolean,
	val entityId: Int?,
	val entityFeet/eyes: Int?
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayPlayerLookAt> {
        override val ID: Int = 0x41
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayPlayerLookAt = ClientPlayPlayerLookAt(
			feet/eyes = reader.readVarInt(),
			targetX = reader.readDouble(),
			targetY = reader.readDouble(),
			targetZ = reader.readDouble(),
			isEntity = reader.readBoolean(),
			entityId = reader.readOptional { reader.readVarInt() },
			entityFeet/eyes = reader.readOptional { reader.readVarInt() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(feet/eyes)
		writer.writeDouble(targetX)
		writer.writeDouble(targetY)
		writer.writeDouble(targetZ)
		writer.writeBoolean(isEntity)
		writer.writeOptional(entityId) { entityId -> writer.writeVarInt(entityId) }
		writer.writeOptional(entityFeet/eyes) { entityFeet/eyes -> writer.writeVarInt(entityFeet/eyes) }
	}
}
