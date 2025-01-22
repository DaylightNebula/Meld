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
class ServerStatusPingRequest(
	val timestamp: Long
): JavaPacket {
    companion object: JavaPacket.Creator<ServerStatusPingRequest> {
        override val ID: Int = 0x01
        override val STATE: JavaConnectionState = JavaConnectionState.STATUS
        override fun decode(reader: AbstractReader): ServerStatusPingRequest = ServerStatusPingRequest(
			timestamp = reader.readLong()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeLong(timestamp)
	}
}
