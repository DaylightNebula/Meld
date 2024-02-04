package io.github.daylightnebula.meld.player.packets

import io.github.daylightnebula.meld.entities.EntityType
import io.github.daylightnebula.meld.server.extensions.toAngleByte
import io.github.daylightnebula.meld.server.extensions.toVelocityStep
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import java.util.*

class JavaSpawnPlayerPacket(
    var entityID: Int = 0,
    var uid: UUID = UUID.randomUUID(),
    var position: Vector3f = Vector3f.ZERO,
    var rotation: Vector2f = Vector2f.ZERO
): JavaPacket {
    override val id: Int = 0x01
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeLong(uid.mostSignificantBits)
        writer.writeLong(uid.leastSignificantBits)
        writer.writeVarInt(EntityType.PLAYER.mcID)
        writer.writeDouble(position.x.toDouble())
        writer.writeDouble(position.y.toDouble())
        writer.writeDouble(position.z.toDouble())
        writer.writeByte(rotation.x.toAngleByte())
        writer.writeByte(rotation.y.toAngleByte())
        writer.writeByte(0f.toAngleByte())
        writer.writeVarInt(0)
        writer.writeShort(0f.toVelocityStep())
        writer.writeShort(0f.toVelocityStep())
    }
}