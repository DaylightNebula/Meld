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
class ClientPlaySetEntityData(
	val entityId: Int,
	val metadata: EntityMetadata
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlaySetEntityData> {
        override val ID: Int = 0x5D
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlaySetEntityData = ClientPlaySetEntityData(
			entityId = reader.readVarInt(),
			metadata = reader.readEntityMetadata()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeEntityMetadata(metadata)
	}
}
