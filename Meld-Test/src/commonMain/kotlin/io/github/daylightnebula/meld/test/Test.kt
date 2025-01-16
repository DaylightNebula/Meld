package io.github.daylightnebula.meld.test

import io.github.daylightnebula.meld.entities.EntityModule
import io.github.daylightnebula.meld.inventories.InventoryModule
import io.github.daylightnebula.meld.login.LoginModule
import io.github.daylightnebula.meld.player.PlayerModule
import io.github.daylightnebula.meld.server.runMeldServer
import io.github.daylightnebula.meld.world.WorldModule

fun main() {
    println("Running meld...")
    runMeldServer(
        LoginModule(),
        EntityModule(),
        PlayerModule(),
        InventoryModule(),
        WorldModule()
    )
}