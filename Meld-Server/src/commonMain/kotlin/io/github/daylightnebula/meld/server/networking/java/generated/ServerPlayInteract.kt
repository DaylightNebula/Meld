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
class ServerPlayInteract(
	val entityId: Int,
	val type: Int,
	val targetX: Float?,
	val targetY: Float?,
	val targetZ: Float?,
	val hand: Int?,
	val sneakKeyPressed: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayInteract> {
        override val ID: Int = 0x18
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayInteract = ServerPlayInteract(
			entityId = reader.readVarInt(),
			type = reader.readVarInt(),
			targetX = reader.readOptional { reader.readFloat() },
			targetY = reader.readOptional { reader.readFloat() },
			targetZ = reader.readOptional { reader.readFloat() },
			hand = reader.readOptional { reader.readVarInt() },
			sneakKeyPressed = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeVarInt(type)
		writer.writeOptional(targetX) { targetX -> writer.writeFloat(targetX) }
		writer.writeOptional(targetY) { targetY -> writer.writeFloat(targetY) }
		writer.writeOptional(targetZ) { targetZ -> writer.writeFloat(targetZ) }
		writer.writeOptional(hand) { hand -> writer.writeVarInt(hand) }
		writer.writeBoolean(sneakKeyPressed)
	}
}
