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
class ClientPlaySound(
	val soundEvent: ID or,
	val soundCategory: Int,
	val effectPositionX: Int,
	val effectPositionY: Int,
	val effectPositionZ: Int,
	val volume: Float,
	val pitch: Float,
	val seed: Long
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlaySound> {
        override val ID: Int = 0x6F
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlaySound = ClientPlaySound(
			soundEvent = reader.readID or(),
			soundCategory = reader.readVarInt(),
			effectPositionX = reader.readInt(),
			effectPositionY = reader.readInt(),
			effectPositionZ = reader.readInt(),
			volume = reader.readFloat(),
			pitch = reader.readFloat(),
			seed = reader.readLong()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeID or(soundEvent)
		writer.writeVarInt(soundCategory)
		writer.writeInt(effectPositionX)
		writer.writeInt(effectPositionY)
		writer.writeInt(effectPositionZ)
		writer.writeFloat(volume)
		writer.writeFloat(pitch)
		writer.writeLong(seed)
	}
}
