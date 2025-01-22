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
class ClientPlayInitializeBorder(
	val x: Double,
	val z: Double,
	val oldDiameter: Double,
	val newDiameter: Double,
	val speed: Long,
	val portalTeleportBoundary: Int,
	val warningBlocks: Int,
	val warningTime: Int
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayInitializeBorder> {
        override val ID: Int = 0x26
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayInitializeBorder = ClientPlayInitializeBorder(
			x = reader.readDouble(),
			z = reader.readDouble(),
			oldDiameter = reader.readDouble(),
			newDiameter = reader.readDouble(),
			speed = reader.readVarLong(),
			portalTeleportBoundary = reader.readVarInt(),
			warningBlocks = reader.readVarInt(),
			warningTime = reader.readVarInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeDouble(x)
		writer.writeDouble(z)
		writer.writeDouble(oldDiameter)
		writer.writeDouble(newDiameter)
		writer.writeVarLong(speed)
		writer.writeVarInt(portalTeleportBoundary)
		writer.writeVarInt(warningBlocks)
		writer.writeVarInt(warningTime)
	}
}
