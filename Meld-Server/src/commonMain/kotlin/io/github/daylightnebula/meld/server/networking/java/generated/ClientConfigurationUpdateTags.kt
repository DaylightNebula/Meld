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
class ClientConfigurationUpdateTags(
	val registry: Array<String>
): JavaPacket {
    companion object: JavaPacket.Creator<ClientConfigurationUpdateTags> {
        override val ID: Int = 0x0D
        override val STATE: JavaConnectionState = JavaConnectionState.CONFIG
        override fun decode(reader: AbstractReader): ClientConfigurationUpdateTags = ClientConfigurationUpdateTags(
			registry = reader.readArray { reader.readString() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeArray(registry) { registry -> writer.writeString(registry) }
	}
}
