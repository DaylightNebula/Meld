package io.github.daylightnebula.inventories

class PlayerInventory: Inventory {
    var selectedSlot: Int = 0
    override val slots: Array<Item?> = arrayOfNulls(46)
    override fun onInventoryChange(changedSlot: Int, changedItem: Item?) {}
}