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
class ClientPlayExplode(
	val x: Double,
	val y: Double,
	val z: Double,
	val hasPlayerVelocity: Boolean,
	val playerVelocityX: Double?,
	val playerVelocityY: Float?,
	val playerVelocityZ: Float?,
	val explosionParticleId: Int,
	val explosionParticleData: ExplosionData,
	val explosionSound: ID or
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayExplode> {
        override val ID: Int = 0x21
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayExplode = ClientPlayExplode(
			x = reader.readDouble(),
			y = reader.readDouble(),
			z = reader.readDouble(),
			hasPlayerVelocity = reader.readBoolean(),
			playerVelocityX = reader.readOptional { reader.readDouble() },
			playerVelocityY = reader.readOptional { reader.readFloat() },
			playerVelocityZ = reader.readOptional { reader.readFloat() },
			explosionParticleId = reader.readVarInt(),
			explosionParticleData = reader.readExplosionData(),
			explosionSound = reader.readID or()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeDouble(x)
		writer.writeDouble(y)
		writer.writeDouble(z)
		writer.writeBoolean(hasPlayerVelocity)
		writer.writeOptional(playerVelocityX) { playerVelocityX -> writer.writeDouble(playerVelocityX) }
		writer.writeOptional(playerVelocityY) { playerVelocityY -> writer.writeFloat(playerVelocityY) }
		writer.writeOptional(playerVelocityZ) { playerVelocityZ -> writer.writeFloat(playerVelocityZ) }
		writer.writeVarInt(explosionParticleId)
		writer.writeExplosionData(explosionParticleData)
		writer.writeID or(explosionSound)
	}
}
