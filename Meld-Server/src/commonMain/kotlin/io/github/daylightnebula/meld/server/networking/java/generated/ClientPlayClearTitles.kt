package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientPlayClearTitles: JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayClearTitles> {
        override val ID: Int = 0x0F
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun create(): ClientPlayClearTitles = ClientPlayClearTitles()
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    override fun encode(writer: ByteWriter) {}
    override fun decode(writer: AbstractReader) {}
}
