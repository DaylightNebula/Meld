package io.github.daylightnebula.meld.core

import io.github.daylightnebula.meld.core.inventories.InventoryBundle
import io.github.daylightnebula.meld.core.login.LoginBundle
import io.github.daylightnebula.meld.core.player.PlayerBundle
import io.github.daylightnebula.meld.core.player.PlayerListener
import io.github.daylightnebula.meld.core.worlds.World
import io.github.daylightnebula.meld.server.PacketHandler
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.MeldModule

class Core: MeldModule {
    override fun onEnable() {
        // bundles
        PacketHandler.register(LoginBundle())
        PacketHandler.register(PlayerBundle())
        PacketHandler.register(InventoryBundle())

        // event listener
        EventBus.register(PlayerListener())

        // world
        println("Loading world...")
        World.init()

        println("Enabled Core")
    }

    override fun onDisable() {

    }
}