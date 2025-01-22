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
class ClientPlayBlockEvent(
	val location: Float3,
	val actionId: UByte,
	val actionParameter: UByte,
	val blockType: Int
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayBlockEvent> {
        override val ID: Int = 0x08
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayBlockEvent = ClientPlayBlockEvent(
			location = reader.readFloat3(),
			actionId = reader.readUByte(),
			actionParameter = reader.readUByte(),
			blockType = reader.readVarInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeFloat3(location)
		writer.writeUByte(actionId)
		writer.writeUByte(actionParameter)
		writer.writeVarInt(blockType)
	}
}
