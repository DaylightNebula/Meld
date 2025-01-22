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
class ClientPlayMapItemData(
	val mapId: Int,
	val scale: Byte,
	val locked: Boolean,
	val prefixedArray}}: Array<Int>?,
	val x: Byte,
	val z: Byte,
	val direction: Byte,
	val displayName: JsonObject?,
	val columns: UByte,
	val rows: UByte?,
	val x: UByte?,
	val z: UByte?,
	val data: Array<UByte>?
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayMapItemData> {
        override val ID: Int = 0x2D
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayMapItemData = ClientPlayMapItemData(
			mapId = reader.readVarInt(),
			scale = reader.readByte(),
			locked = reader.readBoolean(),
			prefixedArray}} = reader.readOptional { reader.readArray { reader.readVarInt() } },
			x = reader.readByte(),
			z = reader.readByte(),
			direction = reader.readByte(),
			displayName = reader.readOptional { reader.readJsonObject() },
			columns = reader.readUByte(),
			rows = reader.readOptional { reader.readUByte() },
			x = reader.readOptional { reader.readUByte() },
			z = reader.readOptional { reader.readUByte() },
			data = reader.readOptional { reader.readArray { reader.readUByte() } }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(mapId)
		writer.writeByte(scale)
		writer.writeBoolean(locked)
		writer.writeOptional(prefixedArray}}) { prefixedArray}} -> writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeVarInt(prefixedArray}}) } }
		writer.writeByte(x)
		writer.writeByte(z)
		writer.writeByte(direction)
		writer.writeOptional(displayName) { displayName -> writer.writeJsonObject(displayName) }
		writer.writeUByte(columns)
		writer.writeOptional(rows) { rows -> writer.writeUByte(rows) }
		writer.writeOptional(x) { x -> writer.writeUByte(x) }
		writer.writeOptional(z) { z -> writer.writeUByte(z) }
		writer.writeOptional(data) { data -> writer.writeArray(data) { data -> writer.writeUByte(data) } }
	}
}
