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
class ServerPlayUseItemOn(
	val hand: Int,
	val location: Float3,
	val face: Int,
	val cursorPositionX: Float,
	val cursorPositionY: Float,
	val cursorPositionZ: Float,
	val insideBlock: Boolean,
	val worldBorderHit: Boolean,
	val sequence: Int
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayUseItemOn> {
        override val ID: Int = 0x3C
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayUseItemOn = ServerPlayUseItemOn(
			hand = reader.readVarInt(),
			location = reader.readFloat3(),
			face = reader.readVarInt(),
			cursorPositionX = reader.readFloat(),
			cursorPositionY = reader.readFloat(),
			cursorPositionZ = reader.readFloat(),
			insideBlock = reader.readBoolean(),
			worldBorderHit = reader.readBoolean(),
			sequence = reader.readVarInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(hand)
		writer.writeFloat3(location)
		writer.writeVarInt(face)
		writer.writeFloat(cursorPositionX)
		writer.writeFloat(cursorPositionY)
		writer.writeFloat(cursorPositionZ)
		writer.writeBoolean(insideBlock)
		writer.writeBoolean(worldBorderHit)
		writer.writeVarInt(sequence)
	}
}
