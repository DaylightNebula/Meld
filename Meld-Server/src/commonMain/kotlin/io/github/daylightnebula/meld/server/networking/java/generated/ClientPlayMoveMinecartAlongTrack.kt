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
class ClientPlayMoveMinecartAlongTrack(
	val entityId: Int,
	val prefixedArray}}: Array<Double>,
	val y: Double,
	val z: Double,
	val velocityX: Double,
	val velocityY: Double,
	val velocityZ: Double,
	val yaw: Float,
	val pitch: Float,
	val weight: Float
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayMoveMinecartAlongTrack> {
        override val ID: Int = 0x31
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayMoveMinecartAlongTrack = ClientPlayMoveMinecartAlongTrack(
			entityId = reader.readVarInt(),
			prefixedArray}} = reader.readArray { reader.readDouble() },
			y = reader.readDouble(),
			z = reader.readDouble(),
			velocityX = reader.readDouble(),
			velocityY = reader.readDouble(),
			velocityZ = reader.readDouble(),
			yaw = reader.readAngle(),
			pitch = reader.readAngle(),
			weight = reader.readFloat()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeDouble(prefixedArray}}) }
		writer.writeDouble(y)
		writer.writeDouble(z)
		writer.writeDouble(velocityX)
		writer.writeDouble(velocityY)
		writer.writeDouble(velocityZ)
		writer.writeAngle(yaw)
		writer.writeAngle(pitch)
		writer.writeFloat(weight)
	}
}
