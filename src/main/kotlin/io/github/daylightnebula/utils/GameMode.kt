package io.github.daylightnebula.utils


enum class GameMode(private val id: Byte, private val canTakeDamage: Boolean) {
    SURVIVAL(0.toByte(), true),
    CREATIVE(1.toByte(), false),
    ADVENTURE(2.toByte(), true),
    SPECTATOR(3.toByte(), false);

    fun id(): Byte {
        return id
    }

    fun canTakeDamage(): Boolean {
        return canTakeDamage
    }

    companion object {
        fun fromId(id: Int): GameMode {
            return when (id) {
                0 -> SURVIVAL
                1 -> CREATIVE
                2 -> ADVENTURE
                3 -> SPECTATOR
                else -> throw IllegalArgumentException("Unknown game mode id: $id")
            }
        }
    }
}
