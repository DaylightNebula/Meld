package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientPlayPlayerInfoUpdate(
	playerActions: Array<PlayerInfoUpdateData>
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayPlayerInfoUpdate> {
        override val ID: Int = 0x40
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun decode(reader: AbstractReader): ClientPlayPlayerInfoUpdate = ClientPlayPlayerInfoUpdate(
			playerActions = reader.readArray<PlayerInfoUpdateData>()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
