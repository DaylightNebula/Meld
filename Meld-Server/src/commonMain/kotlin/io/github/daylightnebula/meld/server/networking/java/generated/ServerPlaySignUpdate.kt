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
class ServerPlaySignUpdate(
	val location: Float3,
	val isFrontText: Boolean,
	val line1: String,
	val line2: String,
	val line3: String,
	val line4: String
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlaySignUpdate> {
        override val ID: Int = 0x39
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlaySignUpdate = ServerPlaySignUpdate(
			location = reader.readFloat3(),
			isFrontText = reader.readBoolean(),
			line1 = reader.readString(),
			line2 = reader.readString(),
			line3 = reader.readString(),
			line4 = reader.readString()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeFloat3(location)
		writer.writeBoolean(isFrontText)
		writer.writeString(line1)
		writer.writeString(line2)
		writer.writeString(line3)
		writer.writeString(line4)
	}
}
