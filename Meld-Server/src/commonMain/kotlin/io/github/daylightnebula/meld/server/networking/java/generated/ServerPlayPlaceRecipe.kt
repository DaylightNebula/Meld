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
class ServerPlayPlaceRecipe(
	val windowId: Int,
	val recipeId: Int,
	val makeAll: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayPlaceRecipe> {
        override val ID: Int = 0x25
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayPlaceRecipe = ServerPlayPlaceRecipe(
			windowId = reader.readVarInt(),
			recipeId = reader.readVarInt(),
			makeAll = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(windowId)
		writer.writeVarInt(recipeId)
		writer.writeBoolean(makeAll)
	}
}
