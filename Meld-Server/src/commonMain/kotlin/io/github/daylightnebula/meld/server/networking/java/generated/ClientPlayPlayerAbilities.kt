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
class ClientPlayPlayerAbilities(
	val flags: Byte,
	val flyingSpeed: Float,
	val fieldOfViewModifier: Float
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayPlayerAbilities> {
        override val ID: Int = 0x3A
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayPlayerAbilities = ClientPlayPlayerAbilities(
			flags = reader.readByte(),
			flyingSpeed = reader.readFloat(),
			fieldOfViewModifier = reader.readFloat()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeByte(flags)
		writer.writeFloat(flyingSpeed)
		writer.writeFloat(fieldOfViewModifier)
	}
}
