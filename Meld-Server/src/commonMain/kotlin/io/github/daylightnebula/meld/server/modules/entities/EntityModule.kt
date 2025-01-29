package io.github.daylightnebula.meld.server.modules.entities

import io.github.daylightnebula.meld.server.entities.updatablesThread
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.MeldModule

class EntityModule: MeldModule {
    override fun onEnable() {
        EventBus.register(EntityListener())
    }
    override fun onDisable() {
        updatablesThread.cancel()
    }
}