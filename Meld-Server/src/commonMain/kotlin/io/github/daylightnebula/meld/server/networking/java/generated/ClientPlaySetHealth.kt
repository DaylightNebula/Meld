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
class ClientPlaySetHealth(
	val health: Float,
	val food: Int,
	val foodSaturation: Float
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlaySetHealth> {
        override val ID: Int = 0x62
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlaySetHealth = ClientPlaySetHealth(
			health = reader.readFloat(),
			food = reader.readVarInt(),
			foodSaturation = reader.readFloat()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeFloat(health)
		writer.writeVarInt(food)
		writer.writeFloat(foodSaturation)
	}
}
