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
class ClientPlayRecipeBookSettings(
	val craftingRecipeBookOpen: Boolean,
	val craftingRecipeBookFilterActive: Boolean,
	val smeltingRecipeBookOpen: Boolean,
	val smeltingRecipeBookFilterActive: Boolean,
	val blastFurnaceRecipeBookOpen: Boolean,
	val blastFurnaceRecipeBookFilterActive: Boolean,
	val smokerRecipeBookOpen: Boolean,
	val smokerRecipeBookFilterActive: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayRecipeBookSettings> {
        override val ID: Int = 0x46
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayRecipeBookSettings = ClientPlayRecipeBookSettings(
			craftingRecipeBookOpen = reader.readBoolean(),
			craftingRecipeBookFilterActive = reader.readBoolean(),
			smeltingRecipeBookOpen = reader.readBoolean(),
			smeltingRecipeBookFilterActive = reader.readBoolean(),
			blastFurnaceRecipeBookOpen = reader.readBoolean(),
			blastFurnaceRecipeBookFilterActive = reader.readBoolean(),
			smokerRecipeBookOpen = reader.readBoolean(),
			smokerRecipeBookFilterActive = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeBoolean(craftingRecipeBookOpen)
		writer.writeBoolean(craftingRecipeBookFilterActive)
		writer.writeBoolean(smeltingRecipeBookOpen)
		writer.writeBoolean(smeltingRecipeBookFilterActive)
		writer.writeBoolean(blastFurnaceRecipeBookOpen)
		writer.writeBoolean(blastFurnaceRecipeBookFilterActive)
		writer.writeBoolean(smokerRecipeBookOpen)
		writer.writeBoolean(smokerRecipeBookFilterActive)
	}
}
