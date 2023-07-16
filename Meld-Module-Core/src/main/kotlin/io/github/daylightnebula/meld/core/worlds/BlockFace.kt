package io.github.daylightnebula.meld.core.worlds

import org.cloudburstmc.math.vector.Vector3i

enum class BlockFace(val offset: Vector3i) {
    BOTTOM(Vector3i.from(0, -1, 0)),
    TOP(Vector3i.from(0, 1, 0)),
    NORTH(Vector3i.from(0, 0, -1)),
    SOUTH(Vector3i.from(0, 0, 1)),
    WEST(Vector3i.from(-1, 0, 0)),
    EAST(Vector3i.from(1, 0, 0))
}