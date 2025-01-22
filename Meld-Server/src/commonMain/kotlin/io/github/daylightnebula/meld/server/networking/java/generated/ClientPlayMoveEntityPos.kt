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
class ClientPlayMoveEntityPos(
	val entityId: Int,
	val deltaX: Short,
	val deltaY: Short,
	val deltaZ: Short,
	val onGround: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayMoveEntityPos> {
        override val ID: Int = 0x2F
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayMoveEntityPos = ClientPlayMoveEntityPos(
			entityId = reader.readVarInt(),
			deltaX = reader.readShort(),
			deltaY = reader.readShort(),
			deltaZ = reader.readShort(),
			onGround = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeShort(deltaX)
		writer.writeShort(deltaY)
		writer.writeShort(deltaZ)
		writer.writeBoolean(onGround)
	}
}
