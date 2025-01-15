package io.github.daylightnebula.meld.login.packets.login

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.noEncode

class JavaLoginAcknowledge: JavaPacket {
    companion object {
        val ID = 0x03
        val TYPE = JavaConnectionState.LOGIN
    }

    override val id: Int = ID
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {}
}