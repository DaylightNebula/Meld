package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ClientPlayPlaceGhostRecipe: JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayPlaceGhostRecipe> {
        override val ID: Int = 0x39
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun create(): ClientPlayPlaceGhostRecipe = ClientPlayPlaceGhostRecipe()
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    override fun encode(writer: ByteWriter) {}
    override fun decode(writer: AbstractReader) {}
}
