package io.github.daylightnebula.meld.player.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.player.PlayerHand

class JavaSwingArmPacket(
    var hand: PlayerHand = PlayerHand.MAIN
): JavaPacket {
    override val id: Int = 0x2F
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        hand = PlayerHand.values()[reader.readVarInt()]
    }
}