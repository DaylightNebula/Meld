package io.github.daylightnebula.meld.test

import io.github.daylightnebula.meld.server.modules.login.LoginModule
import io.github.daylightnebula.meld.server.runMeldServer

fun main() {
    println("Running meld...")
    runMeldServer(
        LoginModule(),
//        EntityModule(),
//        PlayerModule(),
//        InventoryModule(),
//        WorldModule()
    )
}