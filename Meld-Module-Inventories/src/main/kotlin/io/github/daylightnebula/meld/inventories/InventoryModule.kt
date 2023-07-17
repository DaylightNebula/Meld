package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.server.PacketHandler
import io.github.daylightnebula.meld.server.modules.MeldModule

class InventoryModule: MeldModule {
    override fun onEnable() {
        PacketHandler.register(InventoryBundle())
    }

    override fun onDisable() {}
}