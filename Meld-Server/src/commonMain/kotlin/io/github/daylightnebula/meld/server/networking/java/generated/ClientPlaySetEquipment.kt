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
class ClientPlaySetEquipment(
	val entityId: Int,
	val byte}}: Array<Byte>,
	val item: Slot
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlaySetEquipment> {
        override val ID: Int = 0x60
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlaySetEquipment = ClientPlaySetEquipment(
			entityId = reader.readVarInt(),
			byte}} = reader.readArray { reader.readByte() },
			item = reader.readSlot()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeArray(byte}}) { byte}} -> writer.writeByte(byte}}) }
		writer.writeSlot(item)
	}
}
