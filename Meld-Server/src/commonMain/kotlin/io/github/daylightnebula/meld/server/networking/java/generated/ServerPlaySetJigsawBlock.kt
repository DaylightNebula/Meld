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
class ServerPlaySetJigsawBlock(
	val location: Float3,
	val name: String,
	val target: String,
	val pool: String,
	val finalState: String,
	val jointType: String,
	val selectionPriority: Int,
	val placementPriority: Int
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlaySetJigsawBlock> {
        override val ID: Int = 0x37
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlaySetJigsawBlock = ServerPlaySetJigsawBlock(
			location = reader.readFloat3(),
			name = reader.readString(),
			target = reader.readString(),
			pool = reader.readString(),
			finalState = reader.readString(),
			jointType = reader.readString(),
			selectionPriority = reader.readVarInt(),
			placementPriority = reader.readVarInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeFloat3(location)
		writer.writeString(name)
		writer.writeString(target)
		writer.writeString(pool)
		writer.writeString(finalState)
		writer.writeString(jointType)
		writer.writeVarInt(selectionPriority)
		writer.writeVarInt(placementPriority)
	}
}
