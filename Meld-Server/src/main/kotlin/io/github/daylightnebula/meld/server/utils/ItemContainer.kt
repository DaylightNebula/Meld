package io.github.daylightnebula.meld.server.utils

import org.jglrxavpok.hephaistos.nbt.NBTCompound
import javax.swing.plaf.basic.BasicComboBoxUI.ItemHandler

data class ItemContainer(
    val id: Int,
    val count: Byte,
    val nbt: NBTCompound?
)