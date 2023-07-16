package io.github.daylightnebula.meld.server.entities

object EntityController {
    private var id = Int.MIN_VALUE
    fun nextID() = id++
}