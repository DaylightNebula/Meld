package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientPlayLevelChunkWithLight(
	light: LightData
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayLevelChunkWithLight> {
        override val ID: Int = 0x28
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun decode(reader: AbstractReader): ClientPlayLevelChunkWithLight = ClientPlayLevelChunkWithLight(
			light = reader.readLightData()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
