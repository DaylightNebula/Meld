package io.github.daylightnebula.entities

import io.github.daylightnebula.utils.Vector3

abstract class Entity(
    val id: Int = EntityController.nextID(),
    private val position: Vector3 = Vector3(0.0, 0.0, 0.0)
) {
}