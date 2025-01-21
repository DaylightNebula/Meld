package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ServerPlayPlayerInput(
	flags: UByte
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayPlayerInput> {
        override val ID: Int = 0x29
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun decode(reader: AbstractReader): ServerPlayPlayerInput = ServerPlayPlayerInput(
			flags = reader.readUByte()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
