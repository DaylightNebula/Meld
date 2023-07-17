package io.github.daylightnebula.meld.world

import io.github.daylightnebula.meld.world.World
import io.github.daylightnebula.meld.server.modules.MeldModule

class WorldModule: MeldModule {
    override fun onEnable() {
        println("Initializing world...")
        World.init()
        println("Initialized world!")
    }

    override fun onDisable() {}
}