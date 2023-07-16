package io.github.daylightnebula.meld.core.entities

object EntityController {
    private var id = Int.MIN_VALUE
    fun nextID() = id++
}