package io.github.daylightnebula.meld.login

import io.github.daylightnebula.meld.server.PacketHandler
import io.github.daylightnebula.meld.server.modules.MeldModule

class LoginModule: MeldModule {
    override fun onEnable() {
        PacketHandler.register(LoginBundle())
    }

    override fun onDisable() {}
}