package io.github.daylightnebula.meld.entities

import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.modules.MeldModule
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class EntityModule: MeldModule {
    override fun onEnable() {
        EventBus.register(EntityListener())
        updatablesThread.start()
    }
    override fun onDisable() {
        updatablesThread.join(60)
    }
}