package io.github.daylightnebula.meld.server.player

object TeleportCounter {
    private var currentID = 0
    fun nextID(): Int = currentID++
}