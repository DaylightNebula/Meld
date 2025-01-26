package io.github.daylightnebula.meld.test

import io.github.daylightnebula.meld.server.Meld
import io.github.daylightnebula.meld.server.modules.login.LoginModule

fun main() = Meld.runMeldServer(
    LoginModule(),
//    EntityModule(),
//    PlayerModule(),
//    InventoryModule(),
//    WorldModule()
)