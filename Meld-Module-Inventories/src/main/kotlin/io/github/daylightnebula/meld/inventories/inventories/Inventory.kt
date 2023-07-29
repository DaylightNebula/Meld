package io.github.daylightnebula.meld.inventories.inventories

import io.github.daylightnebula.meld.inventories.utils.InventoryType
import io.github.daylightnebula.meld.inventories.utils.inventory
import io.github.daylightnebula.meld.inventories.packets.JavaCloseInventoryPacket
import io.github.daylightnebula.meld.inventories.packets.JavaOpenInventoryPacket
import io.github.daylightnebula.meld.inventories.packets.JavaSetInventoryContentPacket
import io.github.daylightnebula.meld.inventories.packets.JavaSetItemPacket
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.server.NeedsBedrock
import io.github.daylightnebula.meld.server.events.CancellableEvent
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.bedrock.BedrockConnection
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.utils.ItemContainer
import org.json.JSONObject

class Inventory(
    val type: InventoryType,
    val title: String
): BaseInventory {
    override val slots: Array<ItemContainer?> = arrayOfNulls(type.count)

    // list of all watching the inventory
    private val watchers = mutableListOf<Player>()
    fun getWatchers(): List<Player> = watchers

    // function to open an inventory for a watcher
    fun open(player: Player) {
        watchers.add(player)
        player.inventory.openInventory = this

        // send open packets
        val connection = player.connection
        val javaPackets = arrayOf(
            JavaOpenInventoryPacket(1, type, JSONObject().put("text", title)),
            JavaSetInventoryContentPacket(1u, 0, slots, null),
        )
        when(connection) {
            is JavaConnection -> for (packet in javaPackets) connection.sendPacket(packet)
            is BedrockConnection -> NeedsBedrock()
        }

        // send event
        val event = PlayerOpenInventoryEvent(player, this)
        EventBus.callEvent(event)

        // if cancelled, close the inventory
        if (event.cancelled) close(player, noEvent = true)
    }

    // function to close an inventory for a watcher
    fun close(player: Player, noEvent: Boolean = false) {
        closedBy(player)

        // send close packets
        val connection = player.connection
        val javaPacket = JavaCloseInventoryPacket(1u)
        when (connection) {
            is JavaConnection -> connection.sendPacket(javaPacket)
            is BedrockConnection -> NeedsBedrock()
        }
    }

    // closed by the given connection
    fun closedBy(player: Player, noEvent: Boolean = false) {
        watchers.remove(player)
        player.inventory.openInventory = null

        // stop if marked no event
        if (noEvent) return

        // send event
        val event = PlayerCloseInventoryEvent(player, this)
        EventBus.callEvent(event)

        // if event cancelled, open the inventory
        if (event.cancelled) open(player)
    }

    override fun onInventoryChange(changedSlot: Int, changedItemContainer: ItemContainer?, filled: Boolean) {
        // tell all watchers about the change
        val javaPacket =
            if (filled) JavaSetInventoryContentPacket(1u, 0, slots, null)
            else JavaSetItemPacket(1, 0, changedSlot.toShort(), changedItemContainer)
        watchers.forEach {
            when (val connection = it.connection) {
                is JavaConnection -> connection.sendPacket(javaPacket)
                is BedrockConnection -> NeedsBedrock()
            }
        }
    }
}

data class PlayerOpenInventoryEvent(val player: Player, val inventory: Inventory, override var cancelled: Boolean = false): CancellableEvent
data class PlayerCloseInventoryEvent(val player: Player, val inventory: Inventory, override var cancelled: Boolean = false): CancellableEvent