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
class ClientPlayHorseScreenOpen(
	val windowId: Int,
	val slotCount: Int,
	val entityId: Int
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayHorseScreenOpen> {
        override val ID: Int = 0x24
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayHorseScreenOpen = ClientPlayHorseScreenOpen(
			windowId = reader.readVarInt(),
			slotCount = reader.readVarInt(),
			entityId = reader.readInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(windowId)
		writer.writeVarInt(slotCount)
		writer.writeInt(entityId)
	}
}
