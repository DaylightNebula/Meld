package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.server.PacketHandler
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.MeldModule
import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import org.jglrxavpok.hephaistos.nbt.NBTCompound

class InventoryModule: MeldModule {
    override fun onEnable() {
        PacketHandler.register(InventoryBundle())
        EventBus.register(InventoryListener())
    }

    override fun onDisable() {}
}

enum class EquipmentSlot { MAIN_HAND, OFF_HAND, BOOTS, LEGGINGS, CHESTPLATE, HELMET }
