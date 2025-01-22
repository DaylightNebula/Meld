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
class ServerPlayChatSessionUpdate(
	val sessionId: Uuid,
	val expiresAt: Long,
	val publicKey: Array<Byte>,
	val keySignature: Array<Byte>
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayChatSessionUpdate> {
        override val ID: Int = 0x08
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayChatSessionUpdate = ServerPlayChatSessionUpdate(
			sessionId = reader.readUuid(),
			expiresAt = reader.readLong(),
			publicKey = reader.readArray { reader.readByte() },
			keySignature = reader.readArray { reader.readByte() }
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeUuid(sessionId)
		writer.writeLong(expiresAt)
		writer.writeArray(publicKey) { publicKey -> writer.writeByte(publicKey) }
		writer.writeArray(keySignature) { keySignature -> writer.writeByte(keySignature) }
	}
}
