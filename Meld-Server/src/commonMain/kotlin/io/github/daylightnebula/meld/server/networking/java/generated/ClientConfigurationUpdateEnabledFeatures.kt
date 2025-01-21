package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientConfigurationUpdateEnabledFeatures(
	featureFlags: Array<String>
): JavaPacket {
    companion object: JavaPacket.Creator<ClientConfigurationUpdateEnabledFeatures> {
        override val ID: Int = 0x0C
        override val STATE: JavaConnectionState = JavaConnectionState.CONFIG
        override fun decode(reader: AbstractReader): ClientConfigurationUpdateEnabledFeatures = ClientConfigurationUpdateEnabledFeatures(
			featureFlags = reader.readArray<String>()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
