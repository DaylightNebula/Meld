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
    override val id: Int = 0x17
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
        const val ID = 0x18
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
    override val id: Int = 0x19
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        rotation = Float2(reader.readFloat(), reader.readFloat())
        onGround = reader.readBoolean()
    }
}

class JavaSetPlayerPositionPacket( // AKA sync player position
    var position: Float3 = Float3(),
    var rotation: Float2 = Float2(),
    var flags: Byte = 0x00,
    var teleportID: Int = TeleportCounter.nextID()
): JavaPacket {
    override val id: Int = 0x3E
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
    var blockPosition: Float3,
    var rotation: Float
): JavaPacket {
    override val id: Int = 0x54
    override fun decode(reader: AbstractReader) = noDecode()
    override fun encode(writer: ByteWriter) {
        writer.writeBlockPosition(blockPosition)
        writer.writeFloat(rotation)
    }
}
