package io.github.daylightnebula.meld.server.networking.java.packets

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.ksp.data.IReader
import io.github.daylightnebula.meld.ksp.data.RegisterPacket
import io.github.daylightnebula.meld.server.BoolCodec
import io.github.daylightnebula.meld.server.Float3Codec
import io.github.daylightnebula.meld.server.VarIntCodec
import io.github.daylightnebula.meld.server.entities.PlayerInteractType
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

@RegisterPacket("packet_use_entity")
class JavaServerPlayUseEntity(
    val entityId: Int,
    val type: PlayerInteractType,
    val targetPosition: Float3?,
    val hand: Int?,
    val sneakPressed: Boolean
): JavaPacket {

    override val ID: Int = 0x18
    override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME

    override fun encode() = VarIntCodec.encode(entityId) +
            VarIntCodec.encode(type.ordinal) +
            if (type == PlayerInteractType.INTERACT_AT) {
                Float3Codec.encode(targetPosition!!) + VarIntCodec.encode(hand!!)
            } else byteArrayOf() + BoolCodec.encode(sneakPressed)

    companion object: JavaPacket.Creator<JavaServerPlayUseEntity> {
        override val ID: Int = 0x18
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: IReader): JavaServerPlayUseEntity {
            val entityId = VarIntCodec.decode(reader)
            val type = PlayerInteractType.entries[VarIntCodec.decode(reader)]
            val (target, hand) = if (type == PlayerInteractType.INTERACT_AT)
                Float3Codec.decode(reader) to VarIntCodec.decode(reader)
                else null to null
            val sneakPressed = BoolCodec.decode(reader)
            return JavaServerPlayUseEntity(entityId, type, target, hand, sneakPressed)
        }
    }
}