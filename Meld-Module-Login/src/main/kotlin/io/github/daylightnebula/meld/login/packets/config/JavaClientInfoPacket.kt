package io.github.daylightnebula.meld.login.packets.config

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode

class JavaClientInfoPacket(
    var locale: String = "en_us",
    var viewDistance: Int = 0,
    var chatMode: Int = 0,
    var chatColorsEnabled: Boolean = true,
    var skinParts: Byte = 0x00,
    var mainHand: Int = 1,
    var textFilterEnabled: Boolean = false,
    var allowServerListings: Boolean = true
): JavaPacket {
    companion object {
        val ID = 0x00
        val TYPE = JavaConnectionState.CONFIG
    }

    override val id: Int = ID
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        locale = reader.readVarString()
        viewDistance = reader.readByte().toInt()
        chatMode = reader.readVarInt()
        chatColorsEnabled = reader.readBoolean()
        skinParts = reader.readByte()
        mainHand = reader.readVarInt()
        textFilterEnabled = reader.readBoolean()
        allowServerListings = reader.readBoolean()
    }
}