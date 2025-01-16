package io.github.daylightnebula.meld.entities.packets

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.LivingEntity
import io.github.daylightnebula.meld.server.extensions.toAngleByte
import io.github.daylightnebula.meld.server.extensions.toVelocityStep
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class JavaSpawnEntityPacket(
    var entityID: Int = 0,
    var entityUID: Uuid = Uuid.random(),
    var type: Int = 0,
    var position: Float3 = Float3(),
    var rotation: Float2 = Float2(),
    var headYaw: Float = 0f,
    var data: Int = 0,
    var velocity: Float3 = Float3()
): JavaPacket {
    companion object: JavaPacket.Creator<JavaSpawnEntityPacket> {
        override val INCOMING_ID: Int = 0x01
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaSpawnEntityPacket()
    }

    constructor(entity: Entity): this(
        entity.id, entity.uid, entity.type.mcID,
        entity.position, entity.rotation, if (entity is LivingEntity) entity.headYaw else 0f,
        0, entity.velocity
    )

    override val OUTGOING_ID: Int = 0x01
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        entityUID.toLongs { most, least ->
            writer.writeLong(most)
            writer.writeLong(least)
        }
        writer.writeVarInt(type)
        writer.writeDouble(position.x.toDouble())
        writer.writeDouble(position.y.toDouble())
        writer.writeDouble(position.z.toDouble())
        writer.writeByte(rotation.x.toAngleByte())
        writer.writeByte(rotation.y.toAngleByte())
        writer.writeByte(headYaw.toAngleByte())
        writer.writeVarInt(data)
        writer.writeShort(velocity.x.toVelocityStep())
        writer.writeShort(velocity.y.toVelocityStep())
        writer.writeShort(velocity.z.toVelocityStep())
    }
}