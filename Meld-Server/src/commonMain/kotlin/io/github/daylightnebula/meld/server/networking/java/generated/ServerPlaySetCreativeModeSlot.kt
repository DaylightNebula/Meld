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
class ServerPlaySetCreativeModeSlot(
	val slot: Short,
	val clickedItem: Slot
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlaySetCreativeModeSlot> {
        override val ID: Int = 0x36
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlaySetCreativeModeSlot = ServerPlaySetCreativeModeSlot(
			slot = reader.readShort(),
			clickedItem = reader.readSlot()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeShort(slot)
		writer.writeSlot(clickedItem)
	}
}
