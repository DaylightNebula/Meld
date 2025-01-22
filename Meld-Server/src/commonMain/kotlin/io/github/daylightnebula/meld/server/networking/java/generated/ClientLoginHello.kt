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
class ClientLoginHello(
	val serverId: String,
	val publicKey: Array<Byte>,
	val verifyToken: Array<Byte>,
	val shouldAuthenticate: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ClientLoginHello> {
        override val ID: Int = 0x01
        override val STATE: JavaConnectionState = JavaConnectionState.LOGIN
        override fun decode(reader: AbstractReader): ClientLoginHello = ClientLoginHello(
			serverId = reader.readString(),
			publicKey = reader.readArray { reader.readByte() },
			verifyToken = reader.readArray { reader.readByte() },
			shouldAuthenticate = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeString(serverId)
		writer.writeArray(publicKey) { publicKey -> writer.writeByte(publicKey) }
		writer.writeArray(verifyToken) { verifyToken -> writer.writeByte(verifyToken) }
		writer.writeBoolean(shouldAuthenticate)
	}
}
