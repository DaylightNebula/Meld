package io.github.daylightnebula.utils


enum class Direction(private val normalX: Int, private val normalY: Int, private val normalZ: Int) {
    DOWN(0, -1, 0),
    UP(0, 1, 0),
    NORTH(0, 0, -1),
    SOUTH(0, 0, 1),
    WEST(-1, 0, 0),
    EAST(1, 0, 0);

    fun normalX(): Int {
        return normalX
    }

    fun normalY(): Int {
        return normalY
    }

    fun normalZ(): Int {
        return normalZ
    }

    fun opposite(): Direction {
        return when (this) {
            UP -> DOWN
            DOWN -> UP
            EAST -> WEST
            WEST -> EAST
            NORTH -> SOUTH
            SOUTH -> NORTH
        }
    }

    companion object {
        val HORIZONTAL = arrayOf(SOUTH, WEST, NORTH, EAST)
    }
}
