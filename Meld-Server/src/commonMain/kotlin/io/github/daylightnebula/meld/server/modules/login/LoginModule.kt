package io.github.daylightnebula.meld.server.modules.login

import io.github.daylightnebula.meld.server.PacketManager
import io.github.daylightnebula.meld.server.modules.MeldModule

class LoginModule: MeldModule {
    override fun onEnable() {
        PacketManager.register(LoginBundle())
        println("Login module enabled!")
    }

    override fun onDisable() {}
}