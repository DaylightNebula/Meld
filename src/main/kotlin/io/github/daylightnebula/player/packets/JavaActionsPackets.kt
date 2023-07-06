package io.github.daylightnebula.player.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noEncode
import io.github.daylightnebula.player.PlayerBlockAction
import io.github.daylightnebula.player.PlayerCommandAction
import org.cloudburstmc.math.vector.Vector3i

class JavaPlayerCommandPacket(
    var entityID: Int = 0,
    var action: PlayerCommandAction = PlayerCommandAction.START_SNEAKING,
    var jumpBoost: Int = 0
): JavaPacket {
    override val id: Int = 0x1E
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        entityID = reader.readVarInt()
        action = PlayerCommandAction.values()[reader.readVarInt()]
        jumpBoost = reader.readVarInt()
    }
}

class JavaBlockActionPacket(
    var action: PlayerBlockAction = PlayerBlockAction.START_DIGGING,
    var blockPosition: Vector3i = Vector3i.ZERO,
    var face: Byte = 0x00,
    var sequence: Int = 0
): JavaPacket {
    override val id: Int = 0x1D
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        action = PlayerBlockAction.values()[reader.readVarInt()]
        blockPosition = reader.readBlockPosition()
        face = reader.readByte()
        sequence = reader.readVarInt()
    }
}