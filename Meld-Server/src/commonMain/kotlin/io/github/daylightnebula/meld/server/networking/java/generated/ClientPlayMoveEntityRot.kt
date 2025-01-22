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
class ClientPlayMoveEntityRot(
	val entityId: Int,
	val yaw: Float,
	val pitch: Float,
	val onGround: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayMoveEntityRot> {
        override val ID: Int = 0x32
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayMoveEntityRot = ClientPlayMoveEntityRot(
			entityId = reader.readVarInt(),
			yaw = reader.readAngle(),
			pitch = reader.readAngle(),
			onGround = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeAngle(yaw)
		writer.writeAngle(pitch)
		writer.writeBoolean(onGround)
	}
}
