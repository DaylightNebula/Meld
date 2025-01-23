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
    companion object: JavaPacket.Creator<JavaClientInfoPacket> {
        override val INCOMING_ID = 0x00
        override val STATE = JavaConnectionState.CONFIG
        override fun create() = JavaClientInfoPacket()
    }

    override val OUTGOING_ID: Int = INCOMING_ID
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        locale = reader.readVarString()
        viewDistance = reader.read().toInt()
        chatMode = reader.readVarInt()
        chatColorsEnabled = reader.readBoolean()
        skinParts = reader.read()
        mainHand = reader.readVarInt()
        textFilterEnabled = reader.readBoolean()
        allowServerListings = reader.readBoolean()
    }
}