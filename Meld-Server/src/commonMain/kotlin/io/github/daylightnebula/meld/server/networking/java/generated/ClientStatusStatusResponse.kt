package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientStatusStatusResponse(
	jsonResponse: String
): JavaPacket {
    companion object: JavaPacket.Creator<ClientStatusStatusResponse> {
        override val ID: Int = 0x00
        override val STATE: JavaConnectionState = JavaConnectionState.STATUS
        override fun decode(reader: AbstractReader): ClientStatusStatusResponse = ClientStatusStatusResponse(
			jsonResponse = reader.readString()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
