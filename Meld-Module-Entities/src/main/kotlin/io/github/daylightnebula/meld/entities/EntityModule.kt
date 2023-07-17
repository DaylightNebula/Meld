package io.github.daylightnebula.meld.entities

import io.github.daylightnebula.meld.server.modules.MeldModule

class EntityModule: MeldModule {
    override fun onEnable() {
        println("Enabled entity module")
    }

    override fun onDisable() {}
}