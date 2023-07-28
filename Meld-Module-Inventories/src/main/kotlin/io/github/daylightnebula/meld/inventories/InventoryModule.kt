package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.server.PacketHandler
import io.github.daylightnebula.meld.server.modules.MeldModule
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import org.jglrxavpok.hephaistos.nbt.NBTCompound

class InventoryModule: MeldModule {
    override fun onEnable() {
        PacketHandler.register(InventoryBundle())
    }

    override fun onDisable() {}
}

enum class EquipmentSlot { MAIN_HAND, OFF_HAND, BOOTS, LEGGINGS, CHESTPLATE, HELMET }

data class ItemContainer(
    val id: Int,
    val count: Byte,
    val nbt: NBTCompound?
)
fun ByteWriter.writeItem(item: ItemContainer?) {
    // write if present
    writeBoolean(item != null)
    if (item != null) {
        // write basic info
        writeVarInt(item.id)
        writeByte(item.count)

        // write nbt if present otherwise a 0
        if (item.nbt != null) writeNBT(item.nbt)
        else writeByte(0x00)
    }
}