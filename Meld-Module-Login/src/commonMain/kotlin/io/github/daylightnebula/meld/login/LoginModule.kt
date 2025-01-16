package io.github.daylightnebula.meld.login

import io.github.daylightnebula.meld.server.PacketManager
import io.github.daylightnebula.meld.server.modules.MeldModule

class LoginModule: MeldModule {
    override fun onEnable() {
        PacketManager.register(LoginBundle())
    }

    override fun onDisable() {}
}