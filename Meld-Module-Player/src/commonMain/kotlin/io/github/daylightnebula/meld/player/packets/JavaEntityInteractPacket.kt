package io.github.daylightnebula.meld.player.packets

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.player.PlayerHand
import io.github.daylightnebula.meld.player.PlayerInteractType
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState

data class JavaEntityInteractPacket(
    var entityID: Int = 0,
    var type: PlayerInteractType = PlayerInteractType.INTERACT,
    var targetPosition: Float3? = null,
    var hand: PlayerHand = PlayerHand.MAIN,
    var sneaking: Boolean = false
): JavaPacket {
    companion object: JavaPacket.Creator<JavaEntityInteractPacket> {
        override val INCOMING_ID: Int = 0x10
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaEntityInteractPacket()
    }

    override val OUTGOING_ID: Int = 0x10
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        // load header
        entityID = reader.readVarInt()
        type = PlayerInteractType.values()[reader.readVarInt()]

        // only load this if interact at
        if (type == PlayerInteractType.INTERACT_AT)
            targetPosition = Float3(
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