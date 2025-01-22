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
class ClientPlayAddEntity(
	val entityId: Int,
	val entityUuid: Uuid,
	val type: Int,
	val x: Double,
	val y: Double,
	val z: Double,
	val pitch: Float,
	val yaw: Float,
	val headYaw: Float,
	val data: Int,
	val velocityX: Short,
	val velocityY: Short,
	val velocityZ: Short
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayAddEntity> {
        override val ID: Int = 0x01
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayAddEntity = ClientPlayAddEntity(
			entityId = reader.readVarInt(),
			entityUuid = reader.readUuid(),
			type = reader.readVarInt(),
			x = reader.readDouble(),
			y = reader.readDouble(),
			z = reader.readDouble(),
			pitch = reader.readAngle(),
			yaw = reader.readAngle(),
			headYaw = reader.readAngle(),
			data = reader.readVarInt(),
			velocityX = reader.readShort(),
			velocityY = reader.readShort(),
			velocityZ = reader.readShort()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeUuid(entityUuid)
		writer.writeVarInt(type)
		writer.writeDouble(x)
		writer.writeDouble(y)
		writer.writeDouble(z)
		writer.writeAngle(pitch)
		writer.writeAngle(yaw)
		writer.writeAngle(headYaw)
		writer.writeVarInt(data)
		writer.writeShort(velocityX)
		writer.writeShort(velocityY)
		writer.writeShort(velocityZ)
	}
}
