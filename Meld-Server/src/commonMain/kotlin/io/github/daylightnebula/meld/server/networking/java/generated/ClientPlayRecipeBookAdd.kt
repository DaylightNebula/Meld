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
class ClientPlayRecipeBookAdd(
	val prefixedArray}}: Array<Int>,
	val display: Recipe Display,
	val groupId: Int,
	val categoryId: Int,
	val ingredients: Array<ID Set>?,
	val flags: Byte,
	val replace: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayRecipeBookAdd> {
        override val ID: Int = 0x44
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayRecipeBookAdd = ClientPlayRecipeBookAdd(
			prefixedArray}} = reader.readArray { reader.readVarInt() },
			display = reader.readRecipe Display(),
			groupId = reader.readVarInt(),
			categoryId = reader.readVarInt(),
			ingredients = reader.readOptional { reader.readArray { reader.readID Set() } },
			flags = reader.readByte(),
			replace = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeArray(prefixedArray}}) { prefixedArray}} -> writer.writeVarInt(prefixedArray}}) }
		writer.writeRecipe Display(display)
		writer.writeVarInt(groupId)
		writer.writeVarInt(categoryId)
		writer.writeOptional(ingredients) { ingredients -> writer.writeArray(ingredients) { ingredients -> writer.writeID Set(ingredients) } }
		writer.writeByte(flags)
		writer.writeBoolean(replace)
	}
}
