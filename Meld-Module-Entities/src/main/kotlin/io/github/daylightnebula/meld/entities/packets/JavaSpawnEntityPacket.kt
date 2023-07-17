package io.github.daylightnebula.meld.entities.packets

import io.github.daylightnebula.meld.entities.Entity
import io.github.daylightnebula.meld.entities.LivingEntity
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import java.util.*

class JavaSpawnEntityPacket(
    var entityID: Int = 0,
    var entityUID: UUID = UUID.randomUUID(),
    var type: Int = 0,
    var position: Vector3f = Vector3f.ZERO,
    var rotation: Vector2f = Vector2f.ZERO,
    var headYaw: Float = 0f,
    var data: Int = 0,
    var velocity: Vector3f = Vector3f.ZERO
): JavaPacket {
    constructor(entity: Entity): this(
        entity.id, entity.uid, entity.type.mcID,
        entity.position, entity.rotation, if (entity is LivingEntity) entity.headYaw else 0f,
        0, entity.velocity
    )

    override val id: Int = 0x01
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeLong(entityUID.mostSignificantBits)
        writer.writeLong(entityUID.leastSignificantBits)
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

fun Float.toAngleByte(): Byte = ((this.coerceIn(-180f, 180f) / 180f) * 256f).toInt().toByte()
fun Float.toVelocityStep(): Short = (this * 8000f).toInt().toShort()