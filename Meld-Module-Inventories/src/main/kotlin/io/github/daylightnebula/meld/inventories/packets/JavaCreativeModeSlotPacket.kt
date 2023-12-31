package io.github.daylightnebula.meld.inventories.packets

import io.github.daylightnebula.meld.server.networking.common.AbstractReader
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.server.noEncode
import io.github.daylightnebula.meld.server.utils.ItemContainer
import org.jglrxavpok.hephaistos.nbt.CompressedProcesser
import org.jglrxavpok.hephaistos.nbt.NBTCompound
import org.jglrxavpok.hephaistos.nbt.NBTReader

class JavaCreativeModeSlotPacket(
    var slot: Int = 0,
    var itemContainer: ItemContainer? = null
): JavaPacket {
    override val id: Int = 0x2B
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

    fun readOptionalNBT(reader: AbstractReader): NBTCompound? {
        val data = reader.readArray(reader.remaining())
        return if (data.size == 1 && data.first() == (0x00).toByte()) null
        else NBTReader(data, CompressedProcesser.NONE).read() as NBTCompound
    }
}

