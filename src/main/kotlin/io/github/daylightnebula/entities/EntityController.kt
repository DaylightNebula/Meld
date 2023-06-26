package io.github.daylightnebula.entities

object EntityController {
    private var id = Int.MIN_VALUE
    fun nextID() = id++
}