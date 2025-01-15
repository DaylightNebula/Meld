package io.github.daylightnebula.meld.server.utils

import net.benwoodworth.knbt.NbtCompound

data class ItemContainer(
    val id: Int,
    val count: Byte,
    val nbt: NbtCompound?
)