package io.github.daylightnebula.meld.player.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.player.PlayerHand
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState

class JavaSwingArmPacket(
    var hand: PlayerHand = PlayerHand.MAIN
): JavaPacket {
    companion object: JavaPacket.Creator<JavaSwingArmPacket> {
        override val INCOMING_ID: Int = 0x3B
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaSwingArmPacket()
    }

    override val OUTGOING_ID: Int = 0x3B
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        hand = PlayerHand.values()[reader.readVarInt()]
    }
}