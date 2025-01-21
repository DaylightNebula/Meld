package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import kotlinx.serialization.json.JsonObject
import net.benwoodworth.knbt.NbtCompound
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ServerConfigurationKeepAlive(
	keepAliveId: Long
): JavaPacket {
    companion object: JavaPacket.Creator<ServerConfigurationKeepAlive> {
        override val ID: Int = 0x04
        override val STATE: JavaConnectionState = JavaConnectionState.CONFIG
        override fun decode(reader: AbstractReader): ServerConfigurationKeepAlive = ServerConfigurationKeepAlive(
			keepAliveId = reader.readLong()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
