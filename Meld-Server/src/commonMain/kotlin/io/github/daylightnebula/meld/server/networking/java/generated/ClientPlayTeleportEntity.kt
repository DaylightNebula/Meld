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
class ClientPlayTeleportEntity(
	val entityId: Int,
	val x: Double,
	val y: Double,
	val z: Double,
	val velocityX: Double,
	val velocityY: Double,
	val velocityZ: Double,
	val yaw: Float,
	val pitch: Float,
	val flags: Teleport Flags,
	val onGround: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayTeleportEntity> {
        override val ID: Int = 0x77
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayTeleportEntity = ClientPlayTeleportEntity(
			entityId = reader.readVarInt(),
			x = reader.readDouble(),
			y = reader.readDouble(),
			z = reader.readDouble(),
			velocityX = reader.readDouble(),
			velocityY = reader.readDouble(),
			velocityZ = reader.readDouble(),
			yaw = reader.readFloat(),
			pitch = reader.readFloat(),
			flags = reader.readTeleport Flags(),
			onGround = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeDouble(x)
		writer.writeDouble(y)
		writer.writeDouble(z)
		writer.writeDouble(velocityX)
		writer.writeDouble(velocityY)
		writer.writeDouble(velocityZ)
		writer.writeFloat(yaw)
		writer.writeFloat(pitch)
		writer.writeTeleport Flags(flags)
		writer.writeBoolean(onGround)
	}
}
