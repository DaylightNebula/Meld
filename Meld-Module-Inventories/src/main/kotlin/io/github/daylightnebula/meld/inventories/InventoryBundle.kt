package io.github.daylightnebula.meld.inventories

import io.github.daylightnebula.meld.inventories.packets.*
import io.github.daylightnebula.meld.inventories.utils.inventory
import io.github.daylightnebula.meld.server.events.Event
import io.github.daylightnebula.meld.server.events.EventBus
import io.github.daylightnebula.meld.server.networking.java.JavaConnectionState
import io.github.daylightnebula.meld.server.networking.java.JavaPacket
import io.github.daylightnebula.meld.player.Player
import io.github.daylightnebula.meld.player.PlayerHand
import io.github.daylightnebula.meld.player.extensions.player
import io.github.daylightnebula.meld.server.PacketBundle
import io.github.daylightnebula.meld.server.PacketHandler
import io.github.daylightnebula.meld.server.events.EventHandler
import io.github.daylightnebula.meld.server.javaGamePacket
import io.github.daylightnebula.meld.server.javaPackets
import io.github.daylightnebula.meld.server.networking.java.JavaConnection
import io.github.daylightnebula.meld.server.utils.BlockFace
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i

class InventoryBundle: PacketBundle {
    override fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, () -> JavaPacket> =
        javaPackets(
            javaGamePacket(0x36) to { JavaUseItemPacket() },
            javaGamePacket(0x35) to { JavaUseItemOnPacket() },
            javaGamePacket(0x2C) to { JavaSetSelectedSlotPacket() },
            javaGamePacket(0x0E) to { JavaCloseInventoryPacket() },
            javaGamePacket(0x2F) to { JavaCreativeModeSlotPacket() }
        )

    @PacketHandler
    fun onUseItem(connection: JavaConnection, packet: JavaUseItemPacket) =
        EventBus.callEvent(PlayerUseItemEvent(packet, connection.player))

    @PacketHandler
    fun onUseItemOn(connection: JavaConnection, packet: JavaUseItemOnPacket) =
        EventBus.callEvent(PlayerUseItemEvent(packet, connection.player)) // todo differentiate

    @PacketHandler
    fun onSetSelectedSlot(connection: JavaConnection, packet: JavaSetSelectedSlotPacket) {
        connection.player.inventory.selectedSlot = packet.slot
    }

    @PacketHandler
    fun onCloseInventory(connection: JavaConnection, packet: JavaCloseInventoryPacket) =
        EventBus.callEvent(PlayerCloseInventoryEvent(connection.player, packet.inventoryID.toInt()))

    @PacketHandler
    fun onSetCreativeModeSlot(connection: JavaConnection, packet: JavaCreativeModeSlotPacket) =
        connection.player.inventory.setItem(packet.slot, packet.itemContainer)
}

data class PlayerUseItemEvent(
    val player: Player,
    val hand: PlayerHand,
    val location: Vector3i?,
    val face: BlockFace?,
    val cursorPosition: Vector3f?,
    val insideBlock: Boolean?,
): Event {
    constructor(packet: JavaUseItemPacket, player: Player): this(player, packet.hand, null, null, null, null)
    constructor(packet: JavaUseItemOnPacket, player: Player): this(player, packet.hand, packet.location, packet.face, packet.cursorPosition, packet.insideBlock)
}

data class PlayerCloseInventoryEvent(val player: Player, val inventoryID: Int): Event