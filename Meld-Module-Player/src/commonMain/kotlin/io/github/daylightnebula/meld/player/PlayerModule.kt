package io.github.daylightnebula.meld.player

import io.github.daylightnebula.meld.server.PacketManager
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.MeldModule

class PlayerModule: MeldModule {
    override fun onEnable() {
        PacketManager.register(PlayerBundle())
        EventBus.register(PlayerListener())
    }

    override fun onDisable() {}
}