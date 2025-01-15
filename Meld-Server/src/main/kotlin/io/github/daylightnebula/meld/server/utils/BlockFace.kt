package io.github.daylightnebula.meld.server.utils

import dev.romainguy.kotlin.math.Float3

enum class BlockFace(val offset: Float3) {
    BOTTOM(Float3(0f,-1f,0f)),
    TOP(Float3(0f,1f,0f)),
    NORTH(Float3(0f,0f,-1f)),
    SOUTH(Float3(0f,0f,1f)),
    WEST(Float3(-1f,0f,0f)),
    EAST(Float3(1f,0f,0f))
}