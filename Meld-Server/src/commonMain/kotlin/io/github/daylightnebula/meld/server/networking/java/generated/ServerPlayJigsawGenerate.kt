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
class ServerPlayJigsawGenerate(
	val location: Float3,
	val levels: Int,
	val keepJigsaws: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayJigsawGenerate> {
        override val ID: Int = 0x19
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayJigsawGenerate = ServerPlayJigsawGenerate(
			location = reader.readFloat3(),
			levels = reader.readVarInt(),
			keepJigsaws = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeFloat3(location)
		writer.writeVarInt(levels)
		writer.writeBoolean(keepJigsaws)
	}
}
