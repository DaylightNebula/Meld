package io.github.daylightnebula.meld.player.packets

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.player.PlayerBlockAction
import io.github.daylightnebula.meld.player.PlayerCommandAction
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.utils.BlockFace

class JavaPlayerCommandPacket(
    var entityID: Int = 0,
    var action: PlayerCommandAction = PlayerCommandAction.START_SNEAKING,
    var jumpBoost: Int = 0
): JavaPacket {
    companion object: JavaPacket.Creator<JavaPlayerCommandPacket> {
        override val INCOMING_ID: Int = 0x0E
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaPlayerCommandPacket()
    }

    override val OUTGOING_ID: Int = 0x0E // todo
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        entityID = reader.readVarInt()
        action = PlayerCommandAction.values()[reader.readVarInt()]
        jumpBoost = reader.readVarInt()
    }
}

class JavaBlockActionPacket(
    var action: PlayerBlockAction = PlayerBlockAction.START_DIGGING,
    var blockPosition: Float3 = Float3(),
    var face: BlockFace = BlockFace.BOTTOM,
    var sequence: Int = 0
): JavaPacket {
    companion object: JavaPacket.Creator<JavaBlockActionPacket> {
        override val INCOMING_ID: Int = 0x08
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaBlockActionPacket()
    }

    override val OUTGOING_ID: Int = 0x08
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        action = PlayerBlockAction.values()[reader.readVarInt()]
        blockPosition = reader.readBlockPosition()
        face = BlockFace.values()[reader.readByte().toInt()]
        sequence = reader.readVarInt()
    }
}