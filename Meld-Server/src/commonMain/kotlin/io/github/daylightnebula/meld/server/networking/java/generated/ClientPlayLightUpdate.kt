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
class ClientPlayLightUpdate(
	val chunkX: Int,
	val chunkZ: Int,
	val data: Light Data
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayLightUpdate> {
        override val ID: Int = 0x2B
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayLightUpdate = ClientPlayLightUpdate(
			chunkX = reader.readVarInt(),
			chunkZ = reader.readVarInt(),
			data = reader.readLight Data()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(chunkX)
		writer.writeVarInt(chunkZ)
		writer.writeLight Data(data)
	}
}
