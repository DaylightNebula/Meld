package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import kotlinx.serialization.json.JsonObject
import net.benwoodworth.knbt.NbtCompound
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ClientPlayRecipeBookSettings(
	smokerRecipeBookFilterActive: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayRecipeBookSettings> {
        override val ID: Int = 0x46
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayRecipeBookSettings = ClientPlayRecipeBookSettings(
			smokerRecipeBookFilterActive = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
