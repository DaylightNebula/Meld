package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.server.PacketManager
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.MeldModule

class InventoryModule: MeldModule {
    override fun onEnable() {
        PacketManager.register(InventoryBundle())
        EventBus.register(InventoryListener())
    }

    override fun onDisable() {}
}

enum class EquipmentSlot { MAIN_HAND, OFF_HAND, BOOTS, LEGGINGS, CHESTPLATE, HELMET }
