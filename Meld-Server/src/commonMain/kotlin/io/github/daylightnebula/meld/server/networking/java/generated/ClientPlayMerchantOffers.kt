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
class ClientPlayMerchantOffers(
	val windowId: Int,
	val tradeitem}}: Array<TradeItem>,
	val outputItem: Slot,
	val inputItem2: TradeItem?,
	val tradeDisabled: Boolean,
	val numberOfTradeUses: Int,
	val maximumNumberOfTradeUses: Int,
	val xp: Int,
	val specialPrice: Int,
	val priceMultiplier: Float,
	val demand: Int,
	val varint}}: Int,
	val experience: Int,
	val isRegularVillager: Boolean,
	val canRestock: Boolean
): JavaPacket {
    companion object: JavaPacket.Creator<ClientPlayMerchantOffers> {
        override val ID: Int = 0x2E
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun decode(reader: AbstractReader): ClientPlayMerchantOffers = ClientPlayMerchantOffers(
			windowId = reader.readVarInt(),
			tradeitem}} = reader.readArray { reader.readTradeItem() },
			outputItem = reader.readSlot(),
			inputItem2 = reader.readOptional { reader.readTradeItem() },
			tradeDisabled = reader.readBoolean(),
			numberOfTradeUses = reader.readInt(),
			maximumNumberOfTradeUses = reader.readInt(),
			xp = reader.readInt(),
			specialPrice = reader.readInt(),
			priceMultiplier = reader.readFloat(),
			demand = reader.readInt(),
			varint}} = reader.readVarInt(),
			experience = reader.readVarInt(),
			isRegularVillager = reader.readBoolean(),
			canRestock = reader.readBoolean()
		)
    }
    
    override val ID: Int = Companion.ID
    override val STATE: JavaConnectionState = Companion.STATE
    
    override fun encode(writer: ByteWriter) {
		writer.writeVarInt(windowId)
		writer.writeArray(tradeitem}}) { tradeitem}} -> writer.writeTradeItem(tradeitem}}) }
		writer.writeSlot(outputItem)
		writer.writeOptional(inputItem2) { inputItem2 -> writer.writeTradeItem(inputItem2) }
		writer.writeBoolean(tradeDisabled)
		writer.writeInt(numberOfTradeUses)
		writer.writeInt(maximumNumberOfTradeUses)
		writer.writeInt(xp)
		writer.writeInt(specialPrice)
		writer.writeFloat(priceMultiplier)
		writer.writeInt(demand)
		writer.writeVarInt(varint}})
		writer.writeVarInt(experience)
		writer.writeBoolean(isRegularVillager)
		writer.writeBoolean(canRestock)
	}
}
