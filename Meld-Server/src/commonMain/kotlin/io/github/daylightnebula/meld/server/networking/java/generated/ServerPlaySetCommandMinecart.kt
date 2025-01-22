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
class ServerPlaySetCommandMinecart(
	val entityId: Int,
	val command: String,
	val trackOutput: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlaySetCommandMinecart> {
        override val ID: Int = 0x35
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlaySetCommandMinecart = ServerPlaySetCommandMinecart(
			entityId = reader.readVarInt(),
			command = reader.readString(),
			trackOutput = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeString(command)
		writer.writeBoolean(trackOutput)
	}
}
