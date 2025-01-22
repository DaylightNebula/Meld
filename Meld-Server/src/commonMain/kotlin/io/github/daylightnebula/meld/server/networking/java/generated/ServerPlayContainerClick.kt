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
class ServerPlayContainerClick(
	val windowId: Int,
	val stateId: Int,
	val slot: Short,
	val button: Byte,
	val mode: Int,
	val slotNumber: Array<Short>,
	val slotData: Slot,
	val carriedItem: Slot
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayContainerClick> {
        override val ID: Int = 0x10
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayContainerClick = ServerPlayContainerClick(
			windowId = reader.readVarInt(),
			stateId = reader.readVarInt(),
			slot = reader.readShort(),
			button = reader.readByte(),
			mode = reader.readVarInt(),
			slotNumber = reader.readArray { reader.readShort() },
			slotData = reader.readSlot(),
			carriedItem = reader.readSlot()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(windowId)
		writer.writeVarInt(stateId)
		writer.writeShort(slot)
		writer.writeByte(button)
		writer.writeVarInt(mode)
		writer.writeArray(slotNumber) { slotNumber -> writer.writeShort(slotNumber) }
		writer.writeSlot(slotData)
		writer.writeSlot(carriedItem)
	}
}
