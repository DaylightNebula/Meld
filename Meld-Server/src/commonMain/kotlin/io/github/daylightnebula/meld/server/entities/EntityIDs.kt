package io.github.daylightnebula.meld.server.entities

object EntityIDs {
    private var id = Int.MIN_VALUE
    fun nextID() = id++
}