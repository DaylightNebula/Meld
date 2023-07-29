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
import io.github.daylightnebula.meld.server.utils.BlockFace
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i

class InventoryBundle: io.github.daylightnebula.meld.server.PacketBundle(
    io.github.daylightnebula.meld.server.bedrock(),
    io.github.daylightnebula.meld.server.java(
        JavaUseItemPacket::class.java.name to { connection, packet ->
            packet as JavaUseItemPacket
            EventBus.callEvent(PlayerUseItemEvent(packet, connection.player))
        },

        JavaUseItemOnPacket::class.java.name to { connection, packet ->
            packet as JavaUseItemOnPacket
            EventBus.callEvent(PlayerUseItemEvent(packet, connection.player))
        },

        JavaSetSelectedSlotPacket::class.java.name to { connection, packet ->
            packet as JavaSetSelectedSlotPacket
            connection.player.inventory.selectedSlot = packet.slot
        },

        JavaCloseInventoryPacket::class.java.name to { connection, packet ->
            packet as JavaCloseInventoryPacket
            EventBus.callEvent(PlayerCloseInventoryEvent(connection.player, packet.inventoryID.toInt()))
        },

        JavaCreativeModeSlotPacket::class.java.name to { connection, packet ->
            packet as JavaCreativeModeSlotPacket
            connection.player.inventory.setItem(packet.slot, packet.itemContainer)
        }
    )
) {
    override fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, () -> JavaPacket> =
        io.github.daylightnebula.meld.server.javaPackets(
            io.github.daylightnebula.meld.server.javaGamePacket(0x32) to { JavaUseItemPacket() },
            io.github.daylightnebula.meld.server.javaGamePacket(0x31) to { JavaUseItemOnPacket() },
            io.github.daylightnebula.meld.server.javaGamePacket(0x28) to { JavaSetSelectedSlotPacket() },
            io.github.daylightnebula.meld.server.javaGamePacket(0x0C) to { JavaCloseInventoryPacket() },
            io.github.daylightnebula.meld.server.javaGamePacket(0x2B) to { JavaCreativeModeSlotPacket() }
        )
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