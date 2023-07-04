package io.github.daylightnebula.player.packets.join

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noEncode
import io.github.daylightnebula.player.PlayerChatMode
import io.github.daylightnebula.player.PlayerMainHand

class JavaClientInfoPacket(
    var locale: String = "en_us",
    var viewDistance: Int = 0,
    var chatMode: PlayerChatMode = PlayerChatMode.ENABLED,
    var chatColorsEnabled: Boolean = true,
    var skinParts: Byte = 0x00,
    var mainHand: PlayerMainHand = PlayerMainHand.RIGHT,
    var textFilterEnabled: Boolean = false,
    var allowServerListings: Boolean = true
): JavaPacket {
    override val id: Int = 0x08
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        locale = reader.readVarString()
        viewDistance = reader.readByte().toInt()
        chatMode = PlayerChatMode.values()[reader.readVarInt()]
        chatColorsEnabled = reader.readBoolean()
        skinParts = reader.readByte()
        mainHand = PlayerMainHand.values()[reader.readVarInt()]
        textFilterEnabled = reader.readBoolean()
        allowServerListings = reader.readBoolean()
    }
}