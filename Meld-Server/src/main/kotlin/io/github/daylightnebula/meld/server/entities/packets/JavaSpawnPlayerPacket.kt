package io.github.daylightnebula.meld.server.entities.packets

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
    override val id: Int = 0x03
    override fun decode(reader: AbstractReader) = io.github.daylightnebula.meld.server.noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(entityID)
        writer.writeLong(uid.mostSignificantBits)
        writer.writeLong(uid.leastSignificantBits)
        writer.writeDouble(position.x.toDouble())
        writer.writeDouble(position.y.toDouble())
        writer.writeDouble(position.z.toDouble())
        writer.writeByte(rotation.x.toAngleByte())
        writer.writeByte(rotation.y.toAngleByte())
    }
}