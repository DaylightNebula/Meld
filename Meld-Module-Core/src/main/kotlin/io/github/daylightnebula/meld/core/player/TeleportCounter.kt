package io.github.daylightnebula.meld.core.player

object TeleportCounter {
    private var currentID = 0
    fun nextID(): Int = currentID++
}