package io.github.daylightnebula.meld.inventories.packets

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.player.PlayerHand
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.server.utils.BlockFace

class JavaUseItemPacket(
    var hand: PlayerHand = PlayerHand.MAIN,
    var sequence: Int = 0
): JavaPacket {
    override val id: Int = 0x32
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        hand = PlayerHand.values()[reader.readVarInt()]
        sequence = reader.readVarInt()
    }
}

class JavaUseItemOnPacket(
    var hand: PlayerHand = PlayerHand.MAIN,
    var location: Float3 = Float3(),
    var face: BlockFace = BlockFace.BOTTOM,
    var cursorPosition: Float3 = Float3(),
    var insideBlock: Boolean = false,
    var sequence: Int = 0
): JavaPacket {
    override val id: Int = 0x31
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        hand = PlayerHand.values()[reader.readVarInt()]
        location = reader.readBlockPosition()
        face = BlockFace.values()[reader.readVarInt()]
        cursorPosition = Float3(reader.readFloat(), reader.readFloat(), reader.readFloat())
        insideBlock = reader.readBoolean()
        sequence = reader.readVarInt()
    }
}