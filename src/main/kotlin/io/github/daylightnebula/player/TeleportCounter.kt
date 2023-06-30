package io.github.daylightnebula.player

object TeleportCounter {
    private var currentID = 0
    fun nextID(): Int = currentID++
}