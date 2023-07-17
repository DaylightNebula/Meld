package io.github.daylightnebula.meld.player

import io.github.daylightnebula.meld.server.PacketHandler
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.MeldModule

class PlayerModule: MeldModule {
    override fun onEnable() {
        PacketHandler.register(PlayerBundle())
        EventBus.register(PlayerListener())
    }

    override fun onDisable() {}
}