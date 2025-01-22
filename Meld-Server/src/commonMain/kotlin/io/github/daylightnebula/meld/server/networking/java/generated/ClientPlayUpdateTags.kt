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
class ClientPlayUpdateTags(
	val prefixedArray}}: Array<String>,
	val tags: Array<RegistryTag>
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayUpdateTags> {
        override val ID: Int = 0x7F
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayUpdateTags = ClientPlayUpdateTags(
			prefixedArray}} = reader.readArray { reader.readString() },
			tags = reader.readArray { reader.readRegistryTag() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeString(prefixedArray}}) }
		writer.writeArray(tags) { tags -> writer.writeRegistryTag(tags) }
	}
}
