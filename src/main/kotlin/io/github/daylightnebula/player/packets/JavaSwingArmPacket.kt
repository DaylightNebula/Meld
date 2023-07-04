package io.github.daylightnebula.player.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noEncode
import io.github.daylightnebula.player.PlayerHand

class JavaSwingArmPacket(
    var hand: PlayerHand = PlayerHand.MAIN
): JavaPacket {
    override val id: Int = 0x2F
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        hand = PlayerHand.values()[reader.readVarInt()]
    }
}