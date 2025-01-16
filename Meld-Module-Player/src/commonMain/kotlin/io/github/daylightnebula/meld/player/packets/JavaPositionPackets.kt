package io.github.daylightnebula.meld.player.packets

import dev.romainguy.kotlin.math.Float2
import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noDecode
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.player.TeleportCounter

class JavaConfirmTeleportPacket(
    var teleportID: Int = 0
): JavaPacket {
    override val id: Int = 0x00
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        teleportID = reader.readVarInt()
    }
}

class JavaReceivePlayerPositionPacket(
    var position: Float3 = Float3(),
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x1C
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        position = Float3(reader.readDouble().toFloat(), reader.readDouble().toFloat(), reader.readDouble().toFloat())
        onGround = reader.readBoolean()
    }
}

class JavaReceivePlayerPositionAndRotationPacket(
    var position: Float3 = Float3(),
    var rotation: Float2 = Float2(),
    var onGround: Boolean = false
): JavaPacket {
    companion object {
        const val ID = 0x1D
    }
    override val id: Int = ID
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        position = Float3(reader.readDouble().toFloat(), reader.readDouble().toFloat(), reader.readDouble().toFloat())
        rotation = Float2(reader.readFloat(), reader.readFloat())
        onGround = reader.readBoolean()
    }
}

class JavaReceivePlayerRotationPacket(
    var rotation: Float2 = Float2(),
    var onGround: Boolean = false
): JavaPacket {
    override val id: Int = 0x1E
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        rotation = Float2(reader.readFloat(), reader.readFloat())
        onGround = reader.readBoolean()
    }
}

class JavaSetPlayerPositionPacket( // AKA sync player position
    var position: Float3 = Float3(),
    var velocity: Float3 = Float3(),
    var rotation: Float2 = Float2(),
    var flags: Byte = 0x00,
    var teleportID: Int = TeleportCounter.nextID()
): JavaPacket {
    override val id: Int = 0x42
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeVarInt(teleportID)
        writer.writeDouble(position.x.toDouble())
        writer.writeDouble(position.y.toDouble())
        writer.writeDouble(position.z.toDouble())
        writer.writeDouble(velocity.x.toDouble())
        writer.writeDouble(velocity.y.toDouble())
        writer.writeDouble(velocity.z.toDouble())
        writer.writeFloat(rotation.x)
        writer.writeFloat(rotation.y)
        writer.writeByteArray(byteArrayOf(0, 0, 0, 0))
    }
}

class JavaSetSpawnPositionPacket(
    var blockPosition: Float3,
    var rotation: Float
): JavaPacket {
    override val id: Int = 0x5B
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeBlockPosition(blockPosition)
        writer.writeFloat(rotation)
    }
}
