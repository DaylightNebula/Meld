package io.github.daylightnebula.meld.server.modules

object ModuleLoader {
    val modules = mutableListOf<MeldModule>()

    fun load(modules: Array<out MeldModule>) {
        this.modules.addAll(modules)
        this.modules.forEach(MeldModule::onEnable)
    }
}

interface MeldModule {
    fun onEnable()
    fun onDisable()
}