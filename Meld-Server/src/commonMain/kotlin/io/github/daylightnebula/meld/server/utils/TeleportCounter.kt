package io.github.daylightnebula.meld.server.utils

object TeleportCounter {
    private var currentID = 0
    fun nextID(): Int = currentID++
}