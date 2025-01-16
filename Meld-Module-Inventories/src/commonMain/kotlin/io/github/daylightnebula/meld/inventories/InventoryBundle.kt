package io.github.daylightnebula.meld.inventories

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.inventories.PlayerCloseInventoryEvent
import io.github.daylightnebula.meld.inventories.packets.*
import io.github.daylightnebula.meld.inventories.utils.inventory
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.player.PlayerEntityInteractEvent
import io.github.daylightnebula.meld.player.PlayerHand
import io.github.daylightnebula.meld.player.extensions.player
import io.github.daylightnebula.meld.server.PacketBundle
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.javaPacket
import io.github.daylightnebula.meld.server.javaPackets
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.utils.BlockFace
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InventoryBundle: PacketBundle {
    override fun registerJavaPackets() =
        javaPackets(
            javaPacket(JavaUseItemPacket, this::onUseItem),
            javaPacket(JavaUseItemOnPacket, this::onUseItemOn),
            javaPacket(JavaSetSelectedSlotPacket, this::onSetSelectedSlot),
            javaPacket(JavaCloseInventoryPacket, this::onCloseInventory),
            javaPacket(JavaCreativeModeSlotPacket, this::onSetCreativeModeSlot)
        )

    fun onUseItem(connection: JavaConnection, packet: JavaUseItemPacket) =
        EventBus.callEvent(PlayerUseItemEvent(packet, connection.player))

    fun onUseItemOn(connection: JavaConnection, packet: JavaUseItemOnPacket) =
        EventBus.callEvent(PlayerUseItemEvent(packet, connection.player)) // todo differentiate

    fun onSetSelectedSlot(connection: JavaConnection, packet: JavaSetSelectedSlotPacket) {
        connection.player.inventory.selectedSlot = packet.slot
    }

    fun onCloseInventory(connection: JavaConnection, packet: JavaCloseInventoryPacket) =
        EventBus.callEvent(PlayerCloseInventoryEvent(connection.player, packet.inventoryID.toInt()))

    fun onSetCreativeModeSlot(connection: JavaConnection, packet: JavaCreativeModeSlotPacket) =
        connection.player.inventory.setItem(packet.slot, packet.itemContainer)
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerUseItemEvent(
    val player: Player,
    val hand: PlayerHand,
    val location: Float3?,
    val face: BlockFace?,
    val cursorPosition: Float3?,
    val insideBlock: Boolean?,
): Event {
    companion object: Event.Data<PlayerUseItemEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerUseItemEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
    constructor(packet: JavaUseItemPacket, player: Player): this(player, packet.hand, null, null, null, null)
    constructor(packet: JavaUseItemOnPacket, player: Player): this(player, packet.hand, packet.location, packet.face, packet.cursorPosition, packet.insideBlock)
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerCloseInventoryEvent(val player: Player, val inventoryID: Int): Event {
    companion object: Event.Data<PlayerCloseInventoryEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerCloseInventoryEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}