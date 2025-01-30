package io.github.daylightnebula.meld.test

import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.modules.entities.EntityModule
import io.github.daylightnebula.meld.server.modules.inventories.InventoryModule
import io.github.daylightnebula.meld.server.modules.login.LoginModule
import io.github.daylightnebula.meld.server.modules.player.PlayerModule
import io.github.daylightnebula.meld.server.modules.world.WorldModule

fun main() = Meld.runMeldServer(
    LoginModule(),
    EntityModule(),
    PlayerModule(),
    InventoryModule(),
    WorldModule()
)