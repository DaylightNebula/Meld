package io.github.daylightnebula.math


enum class BlockFace(direction: Direction) {
    BOTTOM(Direction.DOWN),
    TOP(Direction.UP),
    NORTH(Direction.NORTH),
    SOUTH(Direction.SOUTH),
    WEST(Direction.WEST),
    EAST(Direction.EAST);

    private val direction: Direction

    init {
        this.direction = direction
    }

    fun toDirection(): Direction {
        return direction
    }

    val oppositeFace: BlockFace
        get() = when (this) {
            BOTTOM -> TOP
            TOP -> BOTTOM
            NORTH -> SOUTH
            SOUTH -> NORTH
            WEST -> EAST
            EAST -> WEST
        }
}
