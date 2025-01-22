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
class ClientPlayTakeItemEntity(
	val collectedEntityId: Int,
	val collectorEntityId: Int,
	val pickupItemCount: Int
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayTakeItemEntity> {
        override val ID: Int = 0x76
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayTakeItemEntity = ClientPlayTakeItemEntity(
			collectedEntityId = reader.readVarInt(),
			collectorEntityId = reader.readVarInt(),
			pickupItemCount = reader.readVarInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(collectedEntityId)
		writer.writeVarInt(collectorEntityId)
		writer.writeVarInt(pickupItemCount)
	}
}
