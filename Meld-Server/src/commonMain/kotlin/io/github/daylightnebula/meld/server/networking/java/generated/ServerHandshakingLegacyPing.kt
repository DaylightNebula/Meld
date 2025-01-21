package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ServerHandshakingLegacyPing(
	payload: UByte
): JavaPacket {
    companion object: JavaPacket.Creator<ServerHandshakingLegacyPing> {
        override val ID: Int = 0xFE
        override val STATE: JavaConnectionState = JavaConnectionState.HANDSHAKE
        override fun decode(reader: AbstractReader): ServerHandshakingLegacyPing = ServerHandshakingLegacyPing(
			payload = reader.readUByte()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
