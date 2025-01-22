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
class ClientPlayContainerSetContent(
	val windowId: Int,
	val stateId: Int,
	val slotData: Array<Slot>,
	val carriedItem: Slot
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayContainerSetContent> {
        override val ID: Int = 0x13
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayContainerSetContent = ClientPlayContainerSetContent(
			windowId = reader.readVarInt(),
			stateId = reader.readVarInt(),
			slotData = reader.readArray { reader.readSlot() },
			carriedItem = reader.readSlot()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(windowId)
		writer.writeVarInt(stateId)
		writer.writeArray(slotData) { slotData -> writer.writeSlot(slotData) }
		writer.writeSlot(carriedItem)
	}
}
