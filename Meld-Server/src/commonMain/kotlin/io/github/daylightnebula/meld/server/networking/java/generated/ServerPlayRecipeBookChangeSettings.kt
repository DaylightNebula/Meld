package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ServerPlayRecipeBookChangeSettings: JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayRecipeBookChangeSettings> {
        override val ID: Int = 0x2C
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun create(): ServerPlayRecipeBookChangeSettings = ServerPlayRecipeBookChangeSettings()
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    override fun encode(writer: ByteWriter) {}
    override fun decode(writer: AbstractReader) {}
}
