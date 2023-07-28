package io.github.daylightnebula.meld.server.utils

import io.github.daylightnebula.meld.server.networking.common.ByteWriter
import org.jglrxavpok.hephaistos.nbt.NBTCompound

data class ItemContainer(
    val id: Int,
    val count: Byte,
    val nbt: NBTCompound?
)