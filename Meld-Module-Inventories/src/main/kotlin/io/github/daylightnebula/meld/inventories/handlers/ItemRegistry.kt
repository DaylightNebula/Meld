package io.github.daylightnebula.meld.inventories.handlers

import io.github.daylightnebula.meld.server.utils.ItemContainer

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
val ItemContainer.handler: ItemHandler?
    get() = ItemRegistry.getItemHandler(id, nbt?.getInt("CustomModelData"))