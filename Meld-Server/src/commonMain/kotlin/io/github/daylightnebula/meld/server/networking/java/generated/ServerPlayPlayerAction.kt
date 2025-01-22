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
class ServerPlayPlayerAction(
	val status: Int,
	val location: Float3,
	val face: Byte,
	val sequence: Int
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayPlayerAction> {
        override val ID: Int = 0x27
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayPlayerAction = ServerPlayPlayerAction(
			status = reader.readVarInt(),
			location = reader.readFloat3(),
			face = reader.readByte(),
			sequence = reader.readVarInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(status)
		writer.writeFloat3(location)
		writer.writeByte(face)
		writer.writeVarInt(sequence)
	}
}
