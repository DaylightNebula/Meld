package io.github.daylightnebula.inventories.packets

import io.github.daylightnebula.networking.common.AbstractReader
import io.github.daylightnebula.networking.common.ByteWriter
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.noEncode
import io.github.daylightnebula.player.PlayerHand
import io.github.daylightnebula.worlds.BlockFace
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i

class JavaUseItemPacket(
    var hand: PlayerHand = PlayerHand.MAIN,
    var location: Vector3i = Vector3i.from(0, 0, 0),
    var face: BlockFace = BlockFace.BOTTOM,
    var cursorPosition: Vector3f = Vector3f.ZERO,
    var insideBlock: Boolean = false,
    var sequence: Int = 0
): JavaPacket {
    override val id: Int = 0x31
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        hand = PlayerHand.values()[reader.readVarInt()]
        location = reader.readBlockPosition()
        face = BlockFace.values()[reader.readVarInt()]
        cursorPosition = Vector3f.from(reader.readFloat(), reader.readFloat(), reader.readFloat())
        insideBlock = reader.readBoolean()
        sequence = reader.readVarInt()
    }
}