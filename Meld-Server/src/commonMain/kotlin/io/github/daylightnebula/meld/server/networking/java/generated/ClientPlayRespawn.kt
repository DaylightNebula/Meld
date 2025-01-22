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
class ClientPlayRespawn(
	val dimensionType: Int,
	val dimensionName: String,
	val hashedSeed: Long,
	val gameMode: UByte,
	val previousGameMode: Byte,
	val isDebug: Boolean,
	val isFlat: Boolean,
	val hasDeathLocation: Boolean,
	val deathDimensionName: String?,
	val deathLocation: Float3?,
	val portalCooldown: Int,
	val seaLevel: Int,
	val normalRespawns(afterDeath)KeepNoData;: Byte
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayRespawn> {
        override val ID: Int = 0x47
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayRespawn = ClientPlayRespawn(
			dimensionType = reader.readVarInt(),
			dimensionName = reader.readString(),
			hashedSeed = reader.readLong(),
			gameMode = reader.readUByte(),
			previousGameMode = reader.readByte(),
			isDebug = reader.readBoolean(),
			isFlat = reader.readBoolean(),
			hasDeathLocation = reader.readBoolean(),
			deathDimensionName = reader.readOptional { reader.readString() },
			deathLocation = reader.readOptional { reader.readFloat3() },
			portalCooldown = reader.readVarInt(),
			seaLevel = reader.readVarInt(),
			normalRespawns(afterDeath)KeepNoData; = reader.readByte()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(dimensionType)
		writer.writeString(dimensionName)
		writer.writeLong(hashedSeed)
		writer.writeUByte(gameMode)
		writer.writeByte(previousGameMode)
		writer.writeBoolean(isDebug)
		writer.writeBoolean(isFlat)
		writer.writeBoolean(hasDeathLocation)
		writer.writeOptional(deathDimensionName) { deathDimensionName -> writer.writeString(deathDimensionName) }
		writer.writeOptional(deathLocation) { deathLocation -> writer.writeFloat3(deathLocation) }
		writer.writeVarInt(portalCooldown)
		writer.writeVarInt(seaLevel)
		writer.writeByte(normalRespawns(afterDeath)KeepNoData;)
	}
}
