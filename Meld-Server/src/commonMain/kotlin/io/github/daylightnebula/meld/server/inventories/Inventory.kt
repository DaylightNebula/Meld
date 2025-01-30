package io.github.daylightnebula.meld.server.inventories

import io.github.daylightnebula.meld.server.entities.Player
import io.github.daylightnebula.meld.server.utils.InventoryType
import io.github.daylightnebula.meld.server.utils.inventory
import io.github.daylightnebula.meld.server.events.CancellableEvent
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.generated.JavaClientPlayCloseWindow
import io.github.daylightnebula.meld.server.generated.JavaClientPlayOpenWindow
import io.github.daylightnebula.meld.server.generated.JavaClientPlaySetSlot
import io.github.daylightnebula.meld.server.generated.JavaClientPlayWindowItems
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.utils.ItemContainer
import io.github.daylightnebula.meld.server.utils.Slot
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import net.benwoodworth.knbt.NbtString
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class Inventory(
    val type: InventoryType,
    val title: String
): BaseInventory {
    override val slots: MutableList<Slot> = MutableList(type.count) { Slot.Empty() }

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
            JavaClientPlayOpenWindow(1, type.ordinal, NbtString(title)),
            JavaClientPlayWindowItems(1, 0, slots, Slot.Empty())
        )
        when(connection) {
            is JavaConnection -> for (packet in javaPackets) connection.sendPacket(packet)
//          BEDROCK  is BedrockConnection -> NeedsBedrock()
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
        val javaPacket = JavaClientPlayCloseWindow(1)
        when (connection) {
            is JavaConnection -> connection.sendPacket(javaPacket)
//          BEDROCK  is BedrockConnection -> NeedsBedrock()
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

    override fun onInventoryChange(changedSlot: Int, changedItemContainer: Slot, filled: Boolean) {
        super.onInventoryChange(changedSlot, changedItemContainer, filled)

        // tell all watchers about the change
//        val javaPacket =
//            if (filled) JavaSetInventoryContentPacket(1u, 0, slots, null)
//            else JavaSetItemPacket(1, 0, changedSlot.toShort(), changedItemContainer)
        val javaPacket =
            if (filled) JavaClientPlayWindowItems(1, 0, slots, Slot.Empty())
            else JavaClientPlaySetSlot(1, 0, changedSlot.toShort(), changedItemContainer)
        watchers.forEach {
            when (val connection = it.connection) {
                is JavaConnection -> connection.sendPacket(javaPacket)
//              BEDROCK  is BedrockConnection -> NeedsBedrock()
            }
        }
    }
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerOpenInventoryEvent(val player: Player, val inventory: Inventory, override var cancelled: Boolean = false): CancellableEvent {
    companion object: Event.Data<PlayerOpenInventoryEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerOpenInventoryEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerCloseInventoryEvent(val player: Player, val inventory: Inventory, override var cancelled: Boolean = false): CancellableEvent {
    companion object: Event.Data<PlayerCloseInventoryEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerCloseInventoryEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}
