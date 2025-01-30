package io.github.daylightnebula.meld.server.modules.inventories

import dev.romainguy.kotlin.math.Float3
import io.github.daylightnebula.meld.server.PacketBundle
import io.github.daylightnebula.meld.server.entities.Player
import io.github.daylightnebula.meld.server.entities.PlayerHand
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.generated.JavaServerPlayBlockPlace
import io.github.daylightnebula.meld.server.generated.JavaServerPlayCloseWindow
import io.github.daylightnebula.meld.server.generated.JavaServerPlayHeldItemSlot
import io.github.daylightnebula.meld.server.generated.JavaServerPlayPickItemFromBlock
import io.github.daylightnebula.meld.server.generated.JavaServerPlaySetCreativeSlot
import io.github.daylightnebula.meld.server.generated.JavaServerPlayUseItem
import io.github.daylightnebula.meld.server.javaPacket
import io.github.daylightnebula.meld.server.javaPackets
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.networking.java.packets.JavaServerPlayUseEntity
import io.github.daylightnebula.meld.server.utils.BlockFace
import io.github.daylightnebula.meld.server.utils.inventory
import io.github.daylightnebula.meld.server.utils.player
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InventoryBundle: PacketBundle {
    override fun registerJavaPackets() =
        javaPackets(
            javaPacket(JavaServerPlayUseItem, this::onUseItem),
            javaPacket(JavaServerPlayBlockPlace, this::onUseItemOn),
            javaPacket(JavaServerPlayHeldItemSlot, this::onSetSelectedSlot),
            javaPacket(JavaServerPlayCloseWindow, this::onCloseInventory),
            javaPacket(JavaServerPlaySetCreativeSlot, this::onSetCreativeModeSlot)
        )

    fun onUseItem(connection: JavaConnection, packet: JavaServerPlayUseItem) =
        EventBus.callEvent(PlayerUseItemEvent(packet, connection.player))

    fun onUseItemOn(connection: JavaConnection, packet: JavaServerPlayBlockPlace) =
        EventBus.callEvent(PlayerUseItemEvent(packet, connection.player)) // todo differentiate

    fun onSetSelectedSlot(connection: JavaConnection, packet: JavaServerPlayHeldItemSlot) {
        connection.player.inventory.selectedSlot = packet.slotId.toInt()
    }

    fun onCloseInventory(connection: JavaConnection, packet: JavaServerPlayCloseWindow) =
        EventBus.callEvent(PlayerCloseInventoryEvent(connection.player, packet.windowId.toInt()))

    fun onSetCreativeModeSlot(connection: JavaConnection, packet: JavaServerPlaySetCreativeSlot) =
        connection.player.inventory.setItem(packet.slot.toInt(), packet.item)
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

    constructor(packet: JavaServerPlayUseItem, player: Player): this(
        player = player,
        hand = PlayerHand.entries[packet.hand],
        null, null, null, null
    )

    constructor(packet: JavaServerPlayBlockPlace, player: Player): this(
        player = player,
        hand = PlayerHand.entries[packet.hand],
        location = packet.location,
        face = BlockFace.entries[packet.direction],
        cursorPosition = Float3(packet.cursorX, packet.cursorY, packet.cursorZ),
        insideBlock = packet.insideBlock
    )
}

@OptIn(ExperimentalUuidApi::class)
data class PlayerCloseInventoryEvent(val player: Player, val inventoryID: Int): Event {
    companion object: Event.Data<PlayerCloseInventoryEvent> {
        override val ID: Uuid = Uuid.random()
        override val executors: MutableList<(PlayerCloseInventoryEvent) -> Unit> = mutableListOf()
    }

    override val ID: Uuid = Companion.ID
}