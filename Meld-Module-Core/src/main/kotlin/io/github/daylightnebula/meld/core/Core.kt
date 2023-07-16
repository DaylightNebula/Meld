package io.github.daylightnebula.meld.core

import io.github.daylightnebula.meld.server.modules.MeldModule

class Core: MeldModule {
    override fun onEnable() {
        println("Enabled core")
    }

    override fun onDisable() {

    }
}