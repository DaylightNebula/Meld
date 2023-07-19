package io.github.daylightnebula.meld.player.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.player.TeleportCounter
import org.cloudburstmc.math.vector.Vector2f
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i

class JavaReceivePlayerPositionPacket(
//    var x: Double = 0.0,
//    var y: Double = 0.0,
//    var z: Double = 0.0,
    var position: Vector3f = Vector3f.ZERO,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x14
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        position = Vector3f.from(reader.readDouble().toFloat(), reader.readDouble().toFloat(), reader.readDouble().toFloat())
        onGround = reader.readBoolean()
    }
}

class JavaConfirmTeleportPacket(
    var teleportID: Int = 0
): JavaPacket {
    override val id: Int = 0x00
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        teleportID = reader.readVarInt()
    }
}

class JavaReceivePlayerPositionAndRotationPacket(
    var position: Vector3f = Vector3f.ZERO,
    var rotation: Vector2f = Vector2f.ZERO,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x15
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        position = Vector3f.from(reader.readDouble().toFloat(), reader.readDouble().toFloat(), reader.readDouble().toFloat())
        rotation = Vector2f.from(reader.readFloat(), reader.readFloat())
        onGround = reader.readBoolean()
    }
}

class JavaReceivePlayerRotationPacket(
    var rotation: Vector2f = Vector2f.ZERO,
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x16
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        rotation = Vector2f.from(reader.readFloat(), reader.readFloat())
        onGround = reader.readBoolean()
    }
}

class JavaSetPlayerPositionPacket(
    var position: Vector3f = Vector3f.ZERO,
    var rotation: Vector2f = Vector2f.ZERO,
    var flags: Byte = 0x00,
    var teleportID: Int = TeleportCounter.nextID()
): JavaPacket {
    override val id: Int = 0x3C
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeDouble(position.x.toDouble())
        writer.writeDouble(position.y.toDouble())
        writer.writeDouble(position.z.toDouble())
        writer.writeFloat(rotation.x)
        writer.writeFloat(rotation.y)
        writer.writeByte(flags)
        writer.writeVarInt(teleportID)
    }
}

class JavaSetSpawnPositionPacket(
    var blockPosition: Vector3i,
    var rotation: Float
): JavaPacket {
    override val id: Int = 0x50
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeBlockPosition(blockPosition)
        writer.writeFloat(rotation)
    }
}
