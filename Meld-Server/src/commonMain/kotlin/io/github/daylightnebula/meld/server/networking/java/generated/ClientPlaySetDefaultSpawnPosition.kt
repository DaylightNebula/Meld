package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientPlaySetDefaultSpawnPosition(
	angle: Float
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlaySetDefaultSpawnPosition> {
        override val ID: Int = 0x5B
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun decode(reader: AbstractReader): ClientPlaySetDefaultSpawnPosition = ClientPlaySetDefaultSpawnPosition(
			angle = reader.readFloat()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
