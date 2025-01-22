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
class ServerPlaySetCommandBlock(
	val location: Float3,
	val command: String,
	val mode: {Type|VarInt}} {{Type|Enum}} ,
	val flags: Byte
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlaySetCommandBlock> {
        override val ID: Int = 0x34
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlaySetCommandBlock = ServerPlaySetCommandBlock(
			location = reader.readFloat3(),
			command = reader.readString(),
			mode = reader.read{Type|VarInt}} {{Type|Enum}} (),
			flags = reader.readByte()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeFloat3(location)
		writer.writeString(command)
		writer.write{Type|VarInt}} {{Type|Enum}} (mode)
		writer.writeByte(flags)
	}
}
