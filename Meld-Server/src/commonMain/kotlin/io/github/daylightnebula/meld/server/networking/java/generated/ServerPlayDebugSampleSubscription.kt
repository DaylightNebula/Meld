package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ServerPlayDebugSampleSubscription: JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayDebugSampleSubscription> {
        override val ID: Int = 0x15
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun create(): ServerPlayDebugSampleSubscription = ServerPlayDebugSampleSubscription()
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    override fun encode(writer: ByteWriter) {}
    override fun decode(writer: AbstractReader) {}
}
