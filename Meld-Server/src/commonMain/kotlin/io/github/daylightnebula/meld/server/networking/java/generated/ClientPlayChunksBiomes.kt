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
class ClientPlayChunksBiomes(
	val chunkBiomeData: Array<ChunkBiomeData>
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayChunksBiomes> {
        override val ID: Int = 0x0E
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayChunksBiomes = ClientPlayChunksBiomes(
			chunkBiomeData = reader.readArray { reader.readChunkBiomeData() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeArray(chunkBiomeData) { chunkBiomeData -> writer.writeChunkBiomeData(chunkBiomeData) }
	}
}
