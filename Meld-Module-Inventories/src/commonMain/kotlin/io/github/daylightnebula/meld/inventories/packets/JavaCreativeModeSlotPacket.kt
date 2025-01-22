package io.github.daylightnebula.meld.inventories.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.server.utils.ItemContainer
import net.benwoodworth.knbt.NbtCompound

class JavaCreativeModeSlotPacket(
    var slot: Int = 0,
    var itemContainer: ItemContainer? = null
): JavaPacket {
    companion object: JavaPacket.Creator<JavaCreativeModeSlotPacket> {
        override val INCOMING_ID: Int = 0x2B
        override val STATE: JavaConnectionState = JavaConnectionState.IN_GAME
        override fun create() = JavaCreativeModeSlotPacket()
    }

    override val OUTGOING_ID: Int = 0x2B
    override fun encode(writer: ByteWriter) = noEncode()
    override fun decode(reader: AbstractReader) {
        slot = reader.readShort().toInt()

        val present = reader.readBoolean()
        itemContainer = when(present) {
            false -> null
            true -> ItemContainer(
                reader.readVarInt(),
                reader.readByte(),
                readOptionalNBT(reader)
            )
        }
    }

    fun readOptionalNBT(reader: AbstractReader): NbtCompound? {
        val data = reader.readBytes(reader.remaining())
        return if (data.size == 1 && data.first() == (0x00).toByte()) null
        else TODO() // NBTReader(data, CompressedProcesser.NONE).read() as NBTCompound
    }
}

