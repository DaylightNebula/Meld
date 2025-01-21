package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientLoginLoginCompression(
	threshold: VarInt
): JavaPacket {
    companion object: JavaPacket.Creator<ClientLoginLoginCompression> {
        override val ID: Int = 0x03
        override val STATE: JavaConnectionState = JavaConnectionState.LOGIN
        override fun decode(reader: AbstractReader): ClientLoginLoginCompression = ClientLoginLoginCompression(
			threshold = reader.readInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
