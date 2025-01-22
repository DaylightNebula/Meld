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
class ServerPlaySetStructureBlock(
	val location: Float3,
	val action: Int,
	val mode: Int,
	val name: String,
	val offsetX: {Type|Byte}},
	val offsetY: {Type|Byte}},
	val offsetZ: {Type|Byte}},
	val sizeX: {Type|Byte}},
	val sizeY: {Type|Byte}},
	val sizeZ: {Type|Byte}},
	val mirror: Int,
	val rotation: Int,
	val metadata: String,
	val integrity: Float,
	val eed: {Type|VarLong}},
	val flags: Byte
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlaySetStructureBlock> {
        override val ID: Int = 0x38
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlaySetStructureBlock = ServerPlaySetStructureBlock(
			location = reader.readFloat3(),
			action = reader.readVarInt(),
			mode = reader.readVarInt(),
			name = reader.readString(),
			offsetX = reader.read{Type|Byte}}(),
			offsetY = reader.read{Type|Byte}}(),
			offsetZ = reader.read{Type|Byte}}(),
			sizeX = reader.read{Type|Byte}}(),
			sizeY = reader.read{Type|Byte}}(),
			sizeZ = reader.read{Type|Byte}}(),
			mirror = reader.readVarInt(),
			rotation = reader.readVarInt(),
			metadata = reader.readString(),
			integrity = reader.readFloat(),
			eed = reader.read{Type|VarLong}}(),
			flags = reader.readByte()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeFloat3(location)
		writer.writeVarInt(action)
		writer.writeVarInt(mode)
		writer.writeString(name)
		writer.write{Type|Byte}}(offsetX)
		writer.write{Type|Byte}}(offsetY)
		writer.write{Type|Byte}}(offsetZ)
		writer.write{Type|Byte}}(sizeX)
		writer.write{Type|Byte}}(sizeY)
		writer.write{Type|Byte}}(sizeZ)
		writer.writeVarInt(mirror)
		writer.writeVarInt(rotation)
		writer.writeString(metadata)
		writer.writeFloat(integrity)
		writer.write{Type|VarLong}}(eed)
		writer.writeByte(flags)
	}
}
