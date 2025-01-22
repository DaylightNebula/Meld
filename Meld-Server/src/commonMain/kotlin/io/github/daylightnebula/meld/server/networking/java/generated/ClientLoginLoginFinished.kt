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
class ClientLoginLoginFinished(
	val uuid: Uuid,
	val username: String,
	val property: Array<LoginEntry>
): JavaPacket {
    companion object: JavaPacket.Creator<ClientLoginLoginFinished> {
        override val ID: Int = 0x02
        override val STATE: JavaConnectionState = JavaConnectionState.LOGIN
        override fun decode(reader: AbstractReader): ClientLoginLoginFinished = ClientLoginLoginFinished(
			uuid = reader.readUuid(),
			username = reader.readString(),
			property = reader.readArray { reader.readLoginEntry() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeUuid(uuid)
		writer.writeString(username)
		writer.writeArray(property) { property -> writer.writeLoginEntry(property) }
	}
}
