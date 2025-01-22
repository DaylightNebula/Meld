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
class ServerPlayClientInformation(
	val locale: String,
	val viewDistance: Byte,
	val chatMode: Int,
	val chatColors: Boolean,
	val displayedSkinParts: UByte,
	val mainHand: Int,
	val enableTextFiltering: Boolean,
	val allowServerListings: Boolean,
	val particleStatus: Int
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayClientInformation> {
        override val ID: Int = 0x0C
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayClientInformation = ServerPlayClientInformation(
			locale = reader.readString(),
			viewDistance = reader.readByte(),
			chatMode = reader.readVarInt(),
			chatColors = reader.readBoolean(),
			displayedSkinParts = reader.readUByte(),
			mainHand = reader.readVarInt(),
			enableTextFiltering = reader.readBoolean(),
			allowServerListings = reader.readBoolean(),
			particleStatus = reader.readVarInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeString(locale)
		writer.writeByte(viewDistance)
		writer.writeVarInt(chatMode)
		writer.writeBoolean(chatColors)
		writer.writeUByte(displayedSkinParts)
		writer.writeVarInt(mainHand)
		writer.writeBoolean(enableTextFiltering)
		writer.writeBoolean(allowServerListings)
		writer.writeVarInt(particleStatus)
	}
}
