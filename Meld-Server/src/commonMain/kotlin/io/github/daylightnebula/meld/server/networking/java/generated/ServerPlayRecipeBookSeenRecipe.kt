package io.github.daylightnebula.meld.server.networking.java.generated

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket

class ServerPlayRecipeBookSeenRecipe(
	recipeId: VarInt
): JavaPacket {
    companion object: JavaPacket.Creator<ServerPlayRecipeBookSeenRecipe> {
        override val ID: Int = 0x2D
        override val STATE: JavaConnectionState = JavaConnectionState.PLAY
        override fun decode(reader: AbstractReader): ServerPlayRecipeBookSeenRecipe = ServerPlayRecipeBookSeenRecipe(
			recipeId = reader.readInt()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
    }
}
