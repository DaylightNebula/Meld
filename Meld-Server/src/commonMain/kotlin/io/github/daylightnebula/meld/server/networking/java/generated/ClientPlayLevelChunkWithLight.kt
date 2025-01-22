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
class ClientPlayLevelChunkWithLight(
	val chunkX: Int,
	val chunkZ: Int,
	val data: Chunk Data,
	val light: Light Data
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayLevelChunkWithLight> {
        override val ID: Int = 0x28
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayLevelChunkWithLight = ClientPlayLevelChunkWithLight(
			chunkX = reader.readInt(),
			chunkZ = reader.readInt(),
			data = reader.readChunk Data(),
			light = reader.readLight Data()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeInt(chunkX)
		writer.writeInt(chunkZ)
		writer.writeChunk Data(data)
		writer.writeLight Data(light)
	}
}
