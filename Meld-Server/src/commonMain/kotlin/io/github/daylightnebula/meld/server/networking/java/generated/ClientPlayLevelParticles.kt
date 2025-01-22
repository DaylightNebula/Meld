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
class ClientPlayLevelParticles(
	val longDistance: Boolean,
	val alwaysVisible: Boolean,
	val x: Double,
	val y: Double,
	val z: Double,
	val offsetX: Float,
	val offsetY: Float,
	val offsetZ: Float,
	val maxSpeed: Float,
	val particleCount: Int,
	val particleId: Int,
	val data: ParticleData
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayLevelParticles> {
        override val ID: Int = 0x2A
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayLevelParticles = ClientPlayLevelParticles(
			longDistance = reader.readBoolean(),
			alwaysVisible = reader.readBoolean(),
			x = reader.readDouble(),
			y = reader.readDouble(),
			z = reader.readDouble(),
			offsetX = reader.readFloat(),
			offsetY = reader.readFloat(),
			offsetZ = reader.readFloat(),
			maxSpeed = reader.readFloat(),
			particleCount = reader.readInt(),
			particleId = reader.readVarInt(),
			data = reader.readParticleData()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeBoolean(longDistance)
		writer.writeBoolean(alwaysVisible)
		writer.writeDouble(x)
		writer.writeDouble(y)
		writer.writeDouble(z)
		writer.writeFloat(offsetX)
		writer.writeFloat(offsetY)
		writer.writeFloat(offsetZ)
		writer.writeFloat(maxSpeed)
		writer.writeInt(particleCount)
		writer.writeVarInt(particleId)
		writer.writeParticleData(data)
	}
}
