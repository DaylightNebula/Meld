package io.github.daylightnebula.meld.inventories.handlers

import io.github.daylightnebula.meld.server.utils.ItemContainer
import net.benwoodworth.knbt.`MIGRATION Acknowledge that NbtCompound now has a stricter get`
import net.benwoodworth.knbt.NbtInt

object ItemRegistry {
    private val handlers = hashMapOf<ItemHandlerRef, ItemHandler>()

    // register item handlers
    fun register(vararg newHandlers: ItemHandler) {
        handlers.putAll(newHandlers.map { ItemHandlerRef(it.id, it.customModelID) to it })
    }

    // get item handler
    fun getItemHandler(id: Int, customModelID: Int?): ItemHandler? = handlers[ItemHandlerRef(id, customModelID)]
}

data class ItemHandlerRef(val id: Int, val customModelID: Int?)
@OptIn(`MIGRATION Acknowledge that NbtCompound now has a stricter get`::class)
val ItemContainer.handler: ItemHandler?
    get() = ItemRegistry.getItemHandler(id, (nbt?.get("CustomModelData") as NbtInt).value)
//    get() = ItemRegistry.getItemHandler(id, nbt?.getInt("CustomModelData"))