package io.github.daylightnebula.meld.server.networking.java.generated

import dev.romainguy.kotlin.math.*
import io.github.daylightnebula.meld.server.networking.*
import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import kotlinx.serialization.json.JsonObject
import net.benwoodworth.knbt.NbtCompound
import kotlin.uuid.Uuid
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
class ServerPlayMovePlayerPosRot(
	val x: Double,
	val feetY: Double,
	val z: Double,
	val yaw: Float,
	val pitch: Float,
	val flags: Byte
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayMovePlayerPosRot> {
        override val ID: Int = 0x1D
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ServerPlayMovePlayerPosRot = ServerPlayMovePlayerPosRot(
			x = reader.readDouble(),
			feetY = reader.readDouble(),
			z = reader.readDouble(),
			yaw = reader.readFloat(),
			pitch = reader.readFloat(),
			flags = reader.readByte()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeDouble(x)
		writer.writeDouble(feetY)
		writer.writeDouble(z)
		writer.writeFloat(yaw)
		writer.writeFloat(pitch)
		writer.writeByte(flags)
	}
}
