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
class ClientPlaySoundEntity(
	val soundEvent: ID or,
	val soundCategory: Int,
	val entityId: Int,
	val volume: Float,
	val pitch: Float,
	val seed: Long
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlaySoundEntity> {
        override val ID: Int = 0x6E
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlaySoundEntity = ClientPlaySoundEntity(
			soundEvent = reader.readID or(),
			soundCategory = reader.readVarInt(),
			entityId = reader.readVarInt(),
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
		writer.writeVarInt(entityId)
		writer.writeFloat(volume)
		writer.writeFloat(pitch)
		writer.writeLong(seed)
	}
}
