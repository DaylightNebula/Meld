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
class ClientPlayAddExperienceOrb(
	val entityId: Int,
	val x: Double,
	val y: Double,
	val z: Double,
	val count: Short
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayAddExperienceOrb> {
        override val ID: Int = 0x02
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayAddExperienceOrb = ClientPlayAddExperienceOrb(
			entityId = reader.readVarInt(),
			x = reader.readDouble(),
			y = reader.readDouble(),
			z = reader.readDouble(),
			count = reader.readShort()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(entityId)
		writer.writeDouble(x)
		writer.writeDouble(y)
		writer.writeDouble(z)
		writer.writeShort(count)
	}
}
