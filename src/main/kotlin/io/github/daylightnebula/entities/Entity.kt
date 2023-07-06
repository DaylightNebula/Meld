package io.github.daylightnebula.entities

import org.cloudburstmc.math.vector.Vector3f

// TODO use bedrock protocol libraries math functions
abstract class Entity(
    val id: Int = EntityController.nextID(),
    startPosition: Vector3f = Vector3f.from(0.0, 0.0, 0.0),
    var dimensionID: String = "overworld"
) {
    var position = startPosition
        private set
}