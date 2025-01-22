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
class ClientPlayStopSound(
	val flags: Byte,
	val source: Int?,
	val sound: String?
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayStopSound> {
        override val ID: Int = 0x71
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayStopSound = ClientPlayStopSound(
			flags = reader.readByte(),
			source = reader.readOptional { reader.readVarInt() },
			sound = reader.readOptional { reader.readString() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeByte(flags)
		writer.writeOptional(source) { source -> writer.writeVarInt(source) }
		writer.writeOptional(sound) { sound -> writer.writeString(sound) }
	}
}
