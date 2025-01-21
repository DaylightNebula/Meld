package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ServerPlaySetBeacon(
	secondaryEffect: VarInt?
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlaySetBeacon> {
        override val ID: Int = 0x32
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun decode(reader: AbstractReader): ServerPlaySetBeacon = ServerPlaySetBeacon(
			secondaryEffect = reader.readInt?()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
