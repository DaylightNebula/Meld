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
class ServerPlaySetBeacon(
	val primaryEffect: Int?,
	val secondaryEffect: Int?
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlaySetBeacon> {
        override val ID: Int = 0x32
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlaySetBeacon = ServerPlaySetBeacon(
			primaryEffect = reader.readOptional { reader.readVarInt() },
			secondaryEffect = reader.readOptional { reader.readVarInt() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeOptional(primaryEffect) { primaryEffect -> writer.writeVarInt(primaryEffect) }
		writer.writeOptional(secondaryEffect) { secondaryEffect -> writer.writeVarInt(secondaryEffect) }
	}
}
