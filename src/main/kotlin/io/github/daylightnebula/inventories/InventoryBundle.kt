package io.github.daylightnebula.inventories

import io.github.daylightnebula.*
import io.github.daylightnebula.events.Event
import io.github.daylightnebula.events.EventBus
import io.github.daylightnebula.inventories.packets.JavaCloseInventoryPacket
import io.github.daylightnebula.inventories.packets.JavaCreativeModeSlotPacket
import io.github.daylightnebula.inventories.packets.JavaSetSelectedSlotPacket
import io.github.daylightnebula.networking.java.JavaConnectionState
import io.github.daylightnebula.networking.java.JavaPacket
import io.github.daylightnebula.inventories.packets.JavaUseItemPacket
import io.github.daylightnebula.player.Player
import io.github.daylightnebula.player.PlayerHand
import io.github.daylightnebula.worlds.BlockFace
import io.github.daylightnebula.worlds.World
import io.github.daylightnebula.worlds.chunks.toChunkPosition
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.math.vector.Vector3i

class InventoryBundle: PacketBundle(
    bedrock(),
    java(
        JavaUseItemPacket::class.java.name to { connection, packet ->
            packet as JavaUseItemPacket
            EventBus.callEvent(PlayerUseItemEvent(packet, connection.player!!))
        },

        JavaSetSelectedSlotPacket::class.java.name to { connection, packet ->
            packet as JavaSetSelectedSlotPacket
            connection.player!!.inventory.selectedSlot = packet.slot
            // TODO broadcast change
        },

        JavaCloseInventoryPacket::class.java.name to { connection, packet ->
            packet as JavaCloseInventoryPacket
            EventBus.callEvent(PlayerCloseInventoryEvent(connection.player!!, packet.inventoryID.toInt()))
        },

        JavaCreativeModeSlotPacket::class.java.name to { connection, packet ->
            packet as JavaCreativeModeSlotPacket
            connection.player!!.inventory.setItem(packet.slot, packet.item)
            println("Packet creative mode slot ${packet.slot} ${packet.item}")
        }
    )
) {
    override fun registerJavaPackets(): HashMap<Pair<Int, JavaConnectionState>, () -> JavaPacket> = javaPackets(
        javaGamePacket(0x31) to { JavaUseItemPacket() },
        javaGamePacket(0x28) to { JavaSetSelectedSlotPacket() },
        javaGamePacket(0x0C) to { JavaCloseInventoryPacket() },
        javaGamePacket(0x2B) to { JavaCreativeModeSlotPacket() }
    )
}

data class PlayerUseItemEvent(
    val player: Player,
    val hand: PlayerHand,
    val location: Vector3i,
    val face: BlockFace,
    val cursorPosition: Vector3f,
    val insideBlock: Boolean,
): Event {
    constructor(packet: JavaUseItemPacket, player: Player): this(player, packet.hand, packet.location, packet.face, packet.cursorPosition, packet.insideBlock)
}

data class PlayerCloseInventoryEvent(val player: Player, val inventoryID: Int): Event