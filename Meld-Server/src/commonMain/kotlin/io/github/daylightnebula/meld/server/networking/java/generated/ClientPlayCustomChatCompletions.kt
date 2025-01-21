package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientPlayCustomChatCompletions: JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayCustomChatCompletions> {
        override val ID: Int = 0x18
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun create(): ClientPlayCustomChatCompletions = ClientPlayCustomChatCompletions()
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    override fun encode(writer: ByteWriter) {}
    override fun decode(writer: AbstractReader) {}
}
