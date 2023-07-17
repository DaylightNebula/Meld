package io.github.daylightnebula.meld.player.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.player.PlayerHand
import io.github.daylightnebula.meld.player.PlayerInteractType
import org.cloudburstmc.math.vector.Vector3f

data class JavaEntityInteractPacket(
    var entityID: Int = 0,
    var type: PlayerInteractType = PlayerInteractType.INTERACT,
    var targetPosition: Vector3f? = null,
    var hand: PlayerHand = PlayerHand.MAIN,
    var sneaking: Boolean = false
): JavaPacket {
    override val id: Int = 0x10
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        // load header
        entityID = reader.readVarInt()
        type = PlayerInteractType.values()[reader.readVarInt()]

        // only load this if interact at
        if (type == PlayerInteractType.INTERACT_AT)
            targetPosition = Vector3f.from(
                reader.readFloat(),
                reader.readFloat(),
                reader.readFloat()
            )

        // only load hand if interact or interact at
        if (type == PlayerInteractType.INTERACT_AT || type == PlayerInteractType.INTERACT)
            hand = PlayerHand.values()[reader.readVarInt()]

        // sneaking
        sneaking = reader.readBoolean()
    }
}