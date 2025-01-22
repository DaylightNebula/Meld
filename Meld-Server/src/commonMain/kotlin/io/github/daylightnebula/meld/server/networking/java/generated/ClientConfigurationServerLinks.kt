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
class ClientConfigurationServerLinks(
	val links: Array<ServerLink>
): JavaPacket {
    companion object: JavaPacket.Creator<ClientConfigurationServerLinks> {
        override val ID: Int = 0x10
        override val STATE: JavaConnectionState = JavaConnectionState.CONFIG
        override fun decode(reader: AbstractReader): ClientConfigurationServerLinks = ClientConfigurationServerLinks(
			links = reader.readArray { reader.readServerLink() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeArray(links) { links -> writer.writeServerLink(links) }
	}
}
