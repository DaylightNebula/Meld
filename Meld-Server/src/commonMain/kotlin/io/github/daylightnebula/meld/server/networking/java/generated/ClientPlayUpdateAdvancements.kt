package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientPlayUpdateAdvancements(
	value: Advancement
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayUpdateAdvancements> {
        override val ID: Int = 0x7B
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun decode(reader: AbstractReader): ClientPlayUpdateAdvancements = ClientPlayUpdateAdvancements(
			value = reader.readAdvancement()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
