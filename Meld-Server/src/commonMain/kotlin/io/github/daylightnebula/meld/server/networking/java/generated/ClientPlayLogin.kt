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
class ClientPlayLogin(
	val entityId: Int,
	val isHardcore: Boolean,
	val dimensionNames: Array<String>,
	val maxPlayers: Int,
	val viewDistance: Int,
	val simulationDistance: Int,
	val reducedDebugInfo: Boolean,
	val enableRespawnScreen: Boolean,
	val doLimitedCrafting: Boolean,
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
	val enforcesSecureChat: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayLogin> {
        override val ID: Int = 0x2C
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayLogin = ClientPlayLogin(
			entityId = reader.readInt(),
			isHardcore = reader.readBoolean(),
			dimensionNames = reader.readArray { reader.readString() },
			maxPlayers = reader.readVarInt(),
			viewDistance = reader.readVarInt(),
			simulationDistance = reader.readVarInt(),
			reducedDebugInfo = reader.readBoolean(),
			enableRespawnScreen = reader.readBoolean(),
			doLimitedCrafting = reader.readBoolean(),
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
			enforcesSecureChat = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeInt(entityId)
		writer.writeBoolean(isHardcore)
		writer.writeArray(dimensionNames) { dimensionNames -> writer.writeString(dimensionNames) }
		writer.writeVarInt(maxPlayers)
		writer.writeVarInt(viewDistance)
		writer.writeVarInt(simulationDistance)
		writer.writeBoolean(reducedDebugInfo)
		writer.writeBoolean(enableRespawnScreen)
		writer.writeBoolean(doLimitedCrafting)
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
		writer.writeBoolean(enforcesSecureChat)
	}
}
