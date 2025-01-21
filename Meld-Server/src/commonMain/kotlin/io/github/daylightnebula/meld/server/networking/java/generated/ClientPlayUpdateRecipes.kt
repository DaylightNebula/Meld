package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientPlayUpdateRecipes(
	slotDisplay: Slot
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayUpdateRecipes> {
        override val ID: Int = 0x7E
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun decode(reader: AbstractReader): ClientPlayUpdateRecipes = ClientPlayUpdateRecipes(
			slotDisplay = reader.readSlot()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
